package zabolotnyi.springbatchjob.converters

import zabolotnyi.springbatchjob.game.Game
import zabolotnyi.springbatchjob.player.Player
import java.io.File
import java.math.BigDecimal

class TxtConverterStrategy : ConvertStrategy {

    override fun convertToPlayers(file: File): List<Player> = file.run {
        file.readLines().map {
            val fields = it.split("\t")
            Player(playerId = fields[0], age = fields[1].toInt(), email = fields[2])
        }
    }

    override fun convertToGames(file: File): List<Game> = file.run {
        file.readLines().map {
            val fields = it.split("\t")
            println(fields)
            Game(
                gameId = fields[0],
                players = fields[1].toInt(),
                win = BigDecimal(fields[2]),
                loss = BigDecimal(fields[3])
            )
        }
    }
}