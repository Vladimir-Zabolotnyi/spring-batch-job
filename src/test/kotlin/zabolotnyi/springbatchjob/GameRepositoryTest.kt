package zabolotnyi.springbatchjob

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.transaction.support.TransactionTemplate
import zabolotnyi.springbatchjob.game.Game
import zabolotnyi.springbatchjob.game.GameRepository
import java.math.BigDecimal
import java.time.LocalDateTime

@DisplayName("Game Repository Test")
class GameRepositoryTest @Autowired constructor(
    val gameRepository: GameRepository,
    val transactionTemplate: TransactionTemplate
) : IntegrationTest() {

    @BeforeEach
    fun setUp() {
        transactionTemplate.execute { gameRepository.deleteAllInBatch() }
    }

    @Test
    fun `should get players with date of creation after`() {

        // given
        transactionTemplate.execute {
            gameRepository.saveAll(
                listOf(
                    Game(gameId = "1", players = 8, win = BigDecimal.ONE, loss = BigDecimal.TEN),
                    Game(gameId = "2", players = 4, win = BigDecimal.ONE, loss = BigDecimal.TEN),
                    Game(gameId = "3", players = 3, win = BigDecimal.ONE, loss = BigDecimal.TEN),
                    Game(gameId = "4", players = 5, win = BigDecimal.ONE, loss = BigDecimal.TEN),
                )
            )
        }

        // when
        val playerNotFound = gameRepository.findAllByCreatedAtAfter(LocalDateTime.now().plusDays(1))
        val playerFound = gameRepository.findAllByCreatedAtAfter(LocalDateTime.now().minusDays(1))

        // then
        assertThat(playerNotFound.size).isEqualTo(0)
        assertThat(playerFound.size).isEqualTo(4)
    }
}