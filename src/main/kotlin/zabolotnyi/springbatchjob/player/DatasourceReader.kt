package zabolotnyi.springbatchjob.player

import org.springframework.batch.item.ItemReader
import org.springframework.batch.item.support.IteratorItemReader
import zabolotnyi.springbatchjob.converters.CsvDatasourceStrategy
import zabolotnyi.springbatchjob.converters.StrategyFactory
import zabolotnyi.springbatchjob.converters.TxtDatasourceStrategy
import java.io.File

data class DatasourceReader(var path: String = "") : ItemReader<List<String>> {

    private var delegate: ItemReader<List<String>>? = null

    override fun read(): List<String>? {
        if (delegate == null) {
            delegate = IteratorItemReader(getPlayers())
        }
        return delegate!!.read()
    }

    private fun getPlayers(): List<List<String>> {
        val files = File(path).listFiles { dir, name -> name.startsWith("player") }
        if (files?.isNotEmpty() == true) {
            files[0].run {
                return when (this.name.split(".").last()) {
                    "csv" -> StrategyFactory.convertToList(CsvDatasourceStrategy(),this)
                    "txt" -> StrategyFactory.convertToList(TxtDatasourceStrategy(),this)
                    else -> listOf()
                }
            }
        } else return listOf()
    }
}

