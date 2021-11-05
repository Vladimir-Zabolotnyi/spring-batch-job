package zabolotnyi.springbatchjob.converters

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import zabolotnyi.springbatchjob.game.Game
import zabolotnyi.springbatchjob.player.Player
import zabolotnyi.springbatchjob.player.PlayerService
import java.io.File

data class Converter(
    val strategy: ConvertStrategy
) {

    private val logger: Logger = LoggerFactory.getLogger(PlayerService::class.java)

    fun convertToPlayer(file: File): List<Player> {
        logger.info("Converted from file: $file")
        return strategy.convertToPlayers(file)
    }
    fun convertToGame(file: File): List<Game> {
        logger.info("Converted from file: $file")
        return strategy.convertToGames(file)
    }
}