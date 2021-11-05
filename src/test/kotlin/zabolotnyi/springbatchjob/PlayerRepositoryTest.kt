package zabolotnyi.springbatchjob

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.transaction.support.TransactionTemplate
import zabolotnyi.springbatchjob.player.Player
import zabolotnyi.springbatchjob.player.PlayerRepository
import java.time.LocalDateTime

@DisplayName("Player Repository Test")
class PlayerRepositoryTest @Autowired constructor(
    val playerRepository: PlayerRepository,
    val transactionTemplate: TransactionTemplate
) : IntegrationTest() {

    @BeforeEach
    fun setUp() {
        transactionTemplate.execute { playerRepository.deleteAllInBatch() }
    }

    @Test
    fun `should get players with date of creation after`() {

        // given
        transactionTemplate.execute {
            playerRepository.saveAll(
                listOf(
                    Player(playerId = "1x", age = 19, email = "e@com"),
                    Player(playerId = "2w", age = 39, email = "b@com"),
                    Player(playerId = "3d", age = 29, email = "c@com"),
                )
            )
        }

        // when
        val playerNotFound = playerRepository.findAllByCreatedAtAfter(LocalDateTime.now().plusDays(1))
        val playerFound = playerRepository.findAllByCreatedAtAfter(LocalDateTime.now().minusDays(1))

        // then
        assertThat(playerNotFound.size).isEqualTo(0)
        assertThat(playerFound.size).isEqualTo(3)
    }
}