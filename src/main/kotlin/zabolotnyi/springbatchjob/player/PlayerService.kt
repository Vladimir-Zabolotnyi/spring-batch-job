package zabolotnyi.springbatchjob.player

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
class PlayerService @Autowired constructor(
    val playerRepository: PlayerRepository,
    val schedulePlayerJobConfig: SchedulePlayerJobConfig,
    val jobExplorer: JobExplorer,
) {
    private val logger: Logger = LoggerFactory.getLogger(PlayerService::class.java)

    fun getAllPlayers(): List<Player> {
        val players = playerRepository.findAll()
        logger.info("It was found players: $players")
        return players
    }

    fun getLastAddedPlayer(): List<Player> {
        val jobInstance = jobExplorer.getLastJobInstance(schedulePlayerJobConfig.job.name) ?: JobInstance(-1L, "unknown")
        val lastJobExecution: JobExecution = jobExplorer.getLastJobExecution(jobInstance) ?: JobExecution(-1L)
        val createTime = lastJobExecution.createTime
        val lastAddedPlayers =
            playerRepository.findAllByCreatedAtAfter(
                LocalDateTime.ofInstant(
                    createTime.toInstant(),
                    ZoneId.systemDefault()
                )
            )
        logger.info("It was found $lastAddedPlayers")
        return lastAddedPlayers
    }

    fun executeImportPlayerJob() {
        schedulePlayerJobConfig.runJob()
        logger.info("Job was run manually")
    }
}