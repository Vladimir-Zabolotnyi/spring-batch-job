package zabolotnyi.springbatchjob.game

import org.springframework.batch.core.Job
import org.springframework.batch.core.Step
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory
import org.springframework.batch.repeat.RepeatStatus.FINISHED
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import zabolotnyi.springbatchjob.converters.CsvDatasourceStrategy
import zabolotnyi.springbatchjob.converters.StrategyFactory
import zabolotnyi.springbatchjob.converters.TxtDatasourceStrategy
import java.io.File
import java.math.BigDecimal

@Configuration
@EnableBatchProcessing
class ImportGameJobConfig(
    @Value("\${spring-batch-job.path-to-import-file}") private val path: String
) {

    @Bean
    fun importGameJob(
        jobs: JobBuilderFactory,
        importGameStep: Step
    ): Job =
        jobs.get("importGameJob")
            .start(importGameStep)
            .build()

    @Bean
    fun importGameStep(steps: StepBuilderFactory, gameRepository: GameRepository): Step =
        steps.get("gameImportStep")
            .tasklet { contribution, chunkContext ->
                val files = File(path).listFiles { dir, name -> name.startsWith("games") }
                if (files?.isNotEmpty() == true) {
                    files[0].run {
                        val games = when (this.name.split(".").last()) {
                            "csv" -> {
                                StrategyFactory.convertToList(CsvDatasourceStrategy(), this)
                            }
                            "txt" -> StrategyFactory.convertToList(TxtDatasourceStrategy(), this)
                            else -> listOf()
                        }.map {
                            Game(
                                gameId = it[0], players = it[1].toInt(),
                                win = BigDecimal(it[2]), loss = BigDecimal(it[3])
                            )
                        }
                        gameRepository.saveAll(games)
                    }
                }
                FINISHED
            }
            .build()
}
