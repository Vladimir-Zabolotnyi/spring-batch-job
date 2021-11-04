package zabolotnyi.springbatchjob

import org.springframework.batch.item.ItemReader
import org.springframework.batch.item.support.IteratorItemReader

import java.nio.file.Files
import kotlin.io.path.Path


data class CustomItemReader(var path: String = "") : ItemReader<Player> {

    private var delegate: ItemReader<Player>? = null

    override fun read(): Player? {
        if (delegate == null) {
            delegate = IteratorItemReader(players())
        }
        return delegate!!.read()
    }                                                                               

    private fun players(): List<Player> {
        return Files.readAllLines(Path(path)).toListPlayer()
    }
}

private fun String.toPlayer(): Player {
    val fields = this.split(",")
    return Player(playerId = fields[0], age = fields[1].toInt(), email = fields[2])
}

private fun List<String>.toListPlayer(): List<Player> = this.map(String::toPlayer)
