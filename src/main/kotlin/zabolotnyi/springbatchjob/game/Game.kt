package zabolotnyi.springbatchjob.game

import org.hibernate.annotations.CreationTimestamp
import java.math.BigDecimal
import java.time.LocalDateTime
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name = "game")
data class Game(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, updatable = false)
    var id: Long? = null,

    @Column(name = "game_id", nullable = false, unique = true, updatable = false)
    var gameId: String = "undefined",

    @Column(name = "players", nullable = false, unique = true, updatable = false)
    var players: Int = 0,

    @Column(name = "win", nullable = false)
    var win: BigDecimal = BigDecimal("-1"),

    @Column(name = "loss", nullable = false)
    var loss: BigDecimal = BigDecimal("-1"),

    @CreationTimestamp
    @Column(name = "created_at", updatable = false, nullable = false)
    val createdAt: LocalDateTime? = null,
)
