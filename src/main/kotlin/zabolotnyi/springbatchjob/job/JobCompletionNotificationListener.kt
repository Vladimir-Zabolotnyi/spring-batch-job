package zabolotnyi.springbatchjob.job

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.batch.core.BatchStatus
import org.springframework.batch.core.JobExecution
import org.springframework.batch.core.listener.JobExecutionListenerSupport
import org.springframework.stereotype.Component
import zabolotnyi.springbatchjob.player.PlayerRepository

@Component
class JobCompletionNotificationListener(
    private val playerRepository: PlayerRepository,
) : JobExecutionListenerSupport() {

    val log: Logger = LoggerFactory.getLogger(JobCompletionNotificationListener::class.java)

    override fun afterJob(jobExecution: JobExecution) {
        if (jobExecution.status === BatchStatus.COMPLETED) {
            log.info("!!! JOB FINISHED! Time to verify the results")
            playerRepository.findAll()
                .forEach { log.info("Found <$it> in the database.") }
        }
    }
}
