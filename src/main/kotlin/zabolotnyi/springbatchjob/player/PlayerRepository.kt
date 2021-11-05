package zabolotnyi.springbatchjob.player

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.time.LocalDateTime

@Repository
interface PlayerRepository : JpaRepository<Player, Long> {

    fun findAllByCreatedAtAfter(dateTime: LocalDateTime): List<Player>


}
