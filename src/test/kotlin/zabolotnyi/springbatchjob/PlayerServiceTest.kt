package zabolotnyi.springbatchjob

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.transaction.support.TransactionTemplate

@DisplayName("Player Service Test")
class PlayerServiceTest @Autowired constructor(
    val playerService: PlayerService,
    val playerRepository: PlayerRepository,
    val transactionTemplate: TransactionTemplate
) : IntegrationTest() {

    @BeforeEach
    fun setUp() {
        transactionTemplate.execute { playerRepository.deleteAllInBatch() }
    }

    @Test
    fun `should get all player`() {

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

        // when
        val actual = playerService.getAllPlayers()

        //then
        assertThat(actual.size).isEqualTo(actual.size)
    }

}