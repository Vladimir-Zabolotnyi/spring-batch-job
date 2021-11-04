package zabolotnyi.springbatchjob.player

import java.io.File

class TxtConverterStrategy : ConvertStrategy {

    override fun convertToEntity(file: File): List<Player> = file.run {
        file.readLines().map {
            val fields = it.split("\t")
            Player(playerId = fields[0], age = fields[1].toInt(), email = fields[2])
        }
    }
}