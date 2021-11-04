package zabolotnyi.springbatchjob

import org.springframework.batch.core.Job
import org.springframework.batch.core.Step
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory
import org.springframework.batch.core.configuration.annotation.StepScope
import org.springframework.batch.core.launch.support.RunIdIncrementer
import org.springframework.batch.item.ItemReader
import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider
import org.springframework.batch.item.database.JdbcBatchItemWriter
import org.springframework.batch.item.database.builder.JdbcBatchItemWriterBuilder
import org.springframework.batch.item.file.FlatFileItemReader
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper
import org.springframework.batch.repeat.RepeatStatus.FINISHED
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.io.PathResource
import javax.sql.DataSource
import kotlin.io.path.Path

@Configuration
@EnableBatchProcessing
class BatchConfiguration(
    private val writer: CustomWriter,
) {
    val path = "src/main/resources/filesToImport/test-data.csv"


//    @Bean
//    fun writerJdbcDefault(dataSource: DataSource): JdbcBatchItemWriter<Player> =
//        JdbcBatchItemWriterBuilder<Player>()
//            .itemSqlParameterSourceProvider(BeanPropertyItemSqlParameterSourceProvider<Player>())
//            .sql("INSERT INTO player (player_id, email,age) VALUES (:playerId, :email,:age)")
//            .dataSource(dataSource)
//            .build()
//
//    @Bean
//    fun readerFlatDefault(): FlatFileItemReader<Player> =
//        FlatFileItemReaderBuilder<Player>()
//            .name("playerReader")
//            .resource(PathResource("src/main/resources/filesToImport/test-data.csv"))
//            .delimited()
//            .names("playerId", "age", "email")
//            .fieldSetMapper(object : BeanWrapperFieldSetMapper<Player>() {
//                init {
//                    setTargetType(Player::class.java)
//                }
//            })
//            .build()

    @StepScope
    @Bean
    fun customReader(): ItemReader<Player> {
        return CustomItemReader(path)
    }



    @Bean
    fun playerImportStep(steps: StepBuilderFactory): Step =
        steps.get("playerImportStep")
            .chunk<Player, Player>(2)
            .reader(customReader())
            .writer(writer)
            .build()

//    @Bean
//    fun playerDeleteStep(steps: StepBuilderFactory): Step =
//        steps.get("playerDeleteStep")
//            .tasklet { contribution, chunkContext ->
//                PathResource(path).file.delete()
//                FINISHED
//            }
//            .build()

    @Bean
    fun importPlayerJob(
        jobs: JobBuilderFactory,
        listener: JobCompletionNotificationListener,
        playerImportStep: Step,
//        playerDeleteStep: Step
    ): Job =
        jobs.get("importPlayerJob")
            .incrementer(RunIdIncrementer())
            .listener(listener)
            .start(playerImportStep)
//            .next(playerDeleteStep)
            .build()
}
