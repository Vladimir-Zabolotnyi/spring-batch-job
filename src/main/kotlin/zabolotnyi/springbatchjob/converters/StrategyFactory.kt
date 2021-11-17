package zabolotnyi.springbatchjob.converters

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import zabolotnyi.springbatchjob.player.PlayerService
import java.io.File

class StrategyFactory {

    private val logger: Logger = LoggerFactory.getLogger(PlayerService::class.java)

    companion object {
        fun convertToList(strategy: DatasourceStrategy, file: File): List<List<String>> {
            return strategy.readDatasource(file)
        }
    }
}