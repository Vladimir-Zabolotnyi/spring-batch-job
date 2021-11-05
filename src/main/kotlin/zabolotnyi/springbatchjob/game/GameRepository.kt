package zabolotnyi.springbatchjob.game

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.time.LocalDateTime

@Repository
interface GameRepository : JpaRepository<Game, Long> {
    fun findAllByCreatedAtAfter(dateTime: LocalDateTime): List<Game>
}