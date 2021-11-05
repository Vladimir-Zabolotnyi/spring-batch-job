package zabolotnyi.springbatchjob.converters

import zabolotnyi.springbatchjob.game.Game
import zabolotnyi.springbatchjob.player.Player
import java.io.File

interface ConvertStrategy {

    fun convertToPlayers(file: File): List<Player>

    fun convertToGames(file: File): List<Game>
}