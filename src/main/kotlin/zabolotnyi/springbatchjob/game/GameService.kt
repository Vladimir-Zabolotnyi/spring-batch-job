package zabolotnyi.springbatchjob.game

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.batch.core.JobExecution
import org.springframework.batch.core.JobInstance
import org.springframework.batch.core.explore.JobExplorer
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.time.LocalDateTime
import java.time.ZoneId

@Service
class GameService @Autowired constructor(
    val gameRepository: GameRepository,
    val jobExplorer: JobExplorer,
    val scheduleGameJobConfig: ScheduleGameJobConfig
) {
    private val logger: Logger = LoggerFactory.getLogger(GameService::class.java)

    fun getLastAddedGames(): List<Game> {
        val jobInstance = jobExplorer
            .getLastJobInstance(scheduleGameJobConfig.job.name) ?: JobInstance(-1L, "unknown")
        val lastJobExecution: JobExecution = jobExplorer.getLastJobExecution(jobInstance) ?: JobExecution(-1L)
        val createTime = lastJobExecution.createTime
        val lastAddedGames =
            gameRepository.findAllByCreatedAtAfter(
                LocalDateTime.ofInstant(
                    createTime.toInstant(),
                    ZoneId.systemDefault()
                )
            )
        logger.info("It was found $lastAddedGames")
        return lastAddedGames
    }

}