package zabolotnyi.springbatchjob.job

import org.springframework.batch.item.ItemReader
import org.springframework.batch.item.support.IteratorItemReader
import zabolotnyi.springbatchjob.converters.Converter
import zabolotnyi.springbatchjob.converters.CsvConverterStrategy
import zabolotnyi.springbatchjob.player.Player
import zabolotnyi.springbatchjob.converters.TxtConverterStrategy
import java.io.File

data class CustomItemReader(var path: String = "") : ItemReader<Player> {

    private var delegate: ItemReader<Player>? = null

    override fun read(): Player? {
        if (delegate == null) {
            delegate = IteratorItemReader(getPlayers())
        }
        return delegate!!.read()
    }

    private fun getPlayers(): List<Player> {
        File(path).listFiles { dir, name -> name.startsWith("player") }[0].run {
            return when (this.name.split(".").last()) {
                "csv" -> Converter(CsvConverterStrategy()).convertToPlayer(this)
                "txt" -> Converter(TxtConverterStrategy()).convertToPlayer(this)
                else -> listOf()
            }
        }
    }
}

