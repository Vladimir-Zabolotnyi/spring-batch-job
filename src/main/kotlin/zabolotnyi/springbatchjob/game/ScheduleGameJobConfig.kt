package zabolotnyi.springbatchjob.game

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.batch.core.Job
import org.springframework.batch.core.JobParametersBuilder
import org.springframework.batch.core.launch.JobLauncher
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.context.annotation.Configuration
import org.springframework.scheduling.annotation.EnableScheduling
import org.springframework.scheduling.annotation.Scheduled
import java.util.UUID

@Configuration
@EnableScheduling
class ScheduleGameJobConfig @Autowired constructor(
    val jobLauncher: JobLauncher,
    @Qualifier("importGameJob") val job: Job,
) {
    val logger: Logger = LoggerFactory.getLogger(ScheduleGameJobConfig::class.java)

    @Scheduled(cron = "\${spring-batch-job.scheduled-cron-expression}")
    fun runJob() = JobParametersBuilder()
        .addString(
            "JobUUID",
            System.currentTimeMillis().toString() + "/" + UUID.randomUUID().toString()
        )
        .toJobParameters()
        .let { jobLauncher.run(job, it) }
        .let { logger.info("Job ${it.jobId} done. ") }
}