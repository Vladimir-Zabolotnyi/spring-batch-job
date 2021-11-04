package zabolotnyi.springbatchjob.player

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import java.io.File

data class Converter(
    val strategy: ConvertStrategy
) {

    private val logger: Logger = LoggerFactory.getLogger(PlayerService::class.java)

    fun convert(file: File): List<Player> {
        logger.info("Converted from file: $file")
        return strategy.convertToEntity(file)
    }
}