package zabolotnyi.springbatchjob.player

import org.springframework.batch.core.Job
import org.springframework.batch.core.Step
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory
import org.springframework.batch.core.configuration.annotation.StepScope
import org.springframework.batch.item.ItemProcessor
import org.springframework.batch.item.ItemReader
import org.springframework.batch.item.ItemWriter
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
@EnableBatchProcessing
class ImportPlayerJobConfig(
    @Value("\${spring-batch-job.path-to-import-file}") private val path: String
) {

    @Bean
    fun importPlayerJob(
        jobs: JobBuilderFactory,
        playerImportStep: Step,
    ): Job =
        jobs.get("importPlayerJob")
            .start(playerImportStep)
            .build()

    @Bean
    fun playerImportStep(steps: StepBuilderFactory, datasourceWriter: ItemWriter<Player>): Step =
        steps.get("playerImportStep")
            .chunk<List<String>, Player>(2)
            .reader(datasourceReader())
            .processor(datasourceProcessor())
            .writer(datasourceWriter)
            .build()

    @Bean
    @StepScope
    fun datasourceReader(): ItemReader<List<String>> {
        return DatasourceReader(path)
    }

    @Bean
    @StepScope
    fun datasourceProcessor(): ItemProcessor<List<String>, Player> {
        return DatasourceProcessor()
    }

    @Bean
    @StepScope
    fun datasourceWriter(repository: PlayerRepository): ItemWriter<Player> {
        return DatasourceWriter(repository)
    }
}
