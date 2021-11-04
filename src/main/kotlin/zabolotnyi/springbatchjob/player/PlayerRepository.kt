package zabolotnyi.springbatchjob.player

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import java.time.LocalDateTime

@Repository
interface PlayerRepository : JpaRepository<Player, Long> {

    @Query(
        value = "SELECT * FROM Player p ORDER BY p.id DESC LIMIT ?1",
        nativeQuery = true
    )
    fun getLastAddedPlayers(number: Int): List<Player>

    fun findAllByCreatedAtAfter(dateTime: LocalDateTime): List<Player>


}
