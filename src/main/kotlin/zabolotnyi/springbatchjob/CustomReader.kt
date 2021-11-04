package zabolotnyi.springbatchjob

import org.springframework.batch.item.ItemReader
import java.nio.file.Files
import java.nio.file.Path

data class CustomReader(
    val path: Path
) : ItemReader<Player> {

    private val listOfLines = Files.readAllLines(path)
    var index = 0

    override fun read(): Player? {
        return if (index < listOfLines.size) {
            val nextPlayer = listOfLines[index].toPlayer()
            index++
            nextPlayer
        } else null
    }
}

private fun String.toPlayer(): Player {
    val fields = this.split(",")
    return Player(playerId = fields[0], age = fields[1].toInt(), email = fields[2])
}
