package zabolotnyi.springbatchjob.job

import org.springframework.batch.core.Job
import org.springframework.batch.core.Step
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory
import org.springframework.batch.core.configuration.annotation.StepScope
import org.springframework.batch.core.launch.support.RunIdIncrementer
import org.springframework.batch.item.ItemReader
import org.springframework.batch.item.ItemWriter
import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider
import org.springframework.batch.item.database.JdbcBatchItemWriter
import org.springframework.batch.item.database.builder.JdbcBatchItemWriterBuilder
import org.springframework.batch.item.file.FlatFileItemReader
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper
import org.springframework.batch.repeat.RepeatStatus.FINISHED
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.io.PathResource
import zabolotnyi.springbatchjob.converters.Converter
import zabolotnyi.springbatchjob.converters.CsvConverterStrategy
import zabolotnyi.springbatchjob.converters.TxtConverterStrategy
import zabolotnyi.springbatchjob.game.GameRepository
import zabolotnyi.springbatchjob.player.Player
import zabolotnyi.springbatchjob.player.PlayerRepository
import java.io.File
import javax.sql.DataSource

@Configuration
@EnableBatchProcessing
class BatchConfiguration(
   @Value("\${spring-batch-job.path-to-import-file}") private val path: String) {


    @Bean
    fun writerJdbcDefault(dataSource: DataSource): JdbcBatchItemWriter<Player> =
        JdbcBatchItemWriterBuilder<Player>()
            .itemSqlParameterSourceProvider(BeanPropertyItemSqlParameterSourceProvider())
            .sql("INSERT INTO player (player_id, email,age) VALUES (:playerId, :email,:age)")
            .dataSource(dataSource)
            .build()

    @Bean
    fun readerFlatDefault(): FlatFileItemReader<Player> =
        FlatFileItemReaderBuilder<Player>()
            .name("playerReader")
            .resource(PathResource("src/main/resources/filesToImport/players.csv"))
            .delimited()
            .names("playerId", "age", "email")
            .fieldSetMapper(object : BeanWrapperFieldSetMapper<Player>() {
                init {
                    setTargetType(Player::class.java)
                }
            })
            .build()

    @Bean
    @StepScope
    fun customReader(): ItemReader<Player> {
        return CustomItemReader(path)
    }

    @Bean
    @StepScope
    fun customWriter(repository: PlayerRepository): ItemWriter<Player> {
        return CustomItemWriter(repository)
    }

    @Bean
    fun playerImportStep(steps: StepBuilderFactory, customWriter: ItemWriter<Player>): Step =
        steps.get("playerImportStep")
            .chunk<Player, Player>(2)
            .reader(customReader())
            .writer(customWriter)
            .build()


    @Bean
    fun playerDeleteStep(steps: StepBuilderFactory): Step =
        steps.get("playerDeleteStep")
            .tasklet { contribution, chunkContext ->
                PathResource(path).file.delete()
                FINISHED
            }
            .build()

    @Bean
    fun gameImportStep(steps: StepBuilderFactory, gameRepository: GameRepository): Step =
        steps.get("gameImportStep")
            .tasklet { contribution, chunkContext ->
                File(path).listFiles { dir, name -> name.startsWith("games") }[0].run {
                    when (this.name.split(".").last()) {
                        "csv" -> {
                            Converter(CsvConverterStrategy())
                                .convertToGame(this)
                                .run { gameRepository.saveAll(this) }
                        }
                        "txt" -> Converter(TxtConverterStrategy())
                            .convertToGame(this)
                            .run { gameRepository.saveAll(this) }
                        else -> FINISHED
                    }

                }
                FINISHED
            }
            .build()

    @Bean
    fun importPlayerJob(
        jobs: JobBuilderFactory,
        listener: JobCompletionNotificationListener,
        playerImportStep: Step,
        gameImportStep: Step
    ): Job =
        jobs.get("importPlayerJob")
            .incrementer(RunIdIncrementer())
            .listener(listener)
            .start(playerImportStep)
            .next(gameImportStep)
            .build()
}
