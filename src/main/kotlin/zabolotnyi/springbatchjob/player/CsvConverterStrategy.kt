package zabolotnyi.springbatchjob.player

import java.io.File

class CsvConverterStrategy : ConvertStrategy {

    override fun convertToEntity(file: File): List<Player> = file.run {
        file.readLines().map {
            val fields = it.split(",")
            Player(playerId = fields[0], age = fields[1].toInt(), email = fields[2])
        }
    }
}