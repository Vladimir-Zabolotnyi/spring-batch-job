package zabolotnyi.springbatchjob

import org.hibernate.annotations.CreationTimestamp
import java.time.LocalDateTime
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name = "player")
data class Player(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, updatable = false)
    var id: Long? = null,

    @Column(name = "player_id", nullable = false, unique = true, updatable = false)
    var playerId: String = "undefined",

    @Column(name = "email", nullable = false, unique = true, updatable = false)
    var email: String = "undefined",

    @Column(name = "age", nullable = false)
    var age: Int = 0,

    @CreationTimestamp
    @Column(name = "created_at", updatable = false, nullable = false)
    val createdAt: LocalDateTime? = null,
)
