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
    fun `should get last added players by number of their quantity`() {

        // given
        val created = transactionTemplate.execute {
            playerRepository.saveAll(
                listOf(
                    Player(playerId = "1x", age = 19, email = "e@com"),
                    Player(playerId = "2w", age = 39, email = "b@com"),
                    Player(playerId = "3d", age = 29, email = "c@com"),
                    Player(playerId = "4s", age = 79, email = "m@com"),
                    Player(playerId = "5q", age = 49, email = "h@com"),
                    Player(playerId = "6f", age = 59, email = "t@com"),
                    Player(playerId = "7t", age = 69, email = "u@com"),
                )
            )
        } ?: listOf()

        // and
        val numberOfLastAddedPlayers = 5

        // when
        val lastAddedPlayers = playerRepository.getLastAddedPlayers(numberOfLastAddedPlayers)

        // then
        assertThat(lastAddedPlayers.size).isEqualTo(numberOfLastAddedPlayers)
        assertThat(lastAddedPlayers.first().id).isEqualTo(created.last().id)
        assertThat(lastAddedPlayers.last().id)
            .isEqualTo(created[created.lastIndex - numberOfLastAddedPlayers + 1].id)
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