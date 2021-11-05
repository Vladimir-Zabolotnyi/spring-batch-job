package zabolotnyi.springbatchjob

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.transaction.support.TransactionTemplate
import zabolotnyi.springbatchjob.game.GameRepository
import zabolotnyi.springbatchjob.player.PlayerRepository
import java.io.File
import java.util.concurrent.TimeUnit

@DisplayName("Batch job Test")
class BatchJobTest @Autowired constructor(
    val playerRepository: PlayerRepository,
    val gameRepository: GameRepository,
    val transactionTemplate: TransactionTemplate,
) : IntegrationTest() {

    @BeforeEach
    fun setUp() {
        transactionTemplate.execute { playerRepository.deleteAllInBatch() }
        transactionTemplate.execute { gameRepository.deleteAllInBatch() }
    }

    @Test
    fun `should import players and games from testFiles directory`() {

        // when
        TimeUnit.SECONDS.sleep(15)

        // then
        assertThat(playerRepository.findAll().size).isEqualTo(
            File("src/test/resources/testFiles/players.csv").readLines().size
        )
        assertThat(gameRepository.findAll().size).isEqualTo(
            File("src/test/resources/testFiles/games.txt").readLines().size
        )
    }

}