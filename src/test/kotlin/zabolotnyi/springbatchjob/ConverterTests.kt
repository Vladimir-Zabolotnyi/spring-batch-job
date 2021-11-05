package zabolotnyi.springbatchjob

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import zabolotnyi.springbatchjob.converters.CsvDatasourceStrategy
import zabolotnyi.springbatchjob.converters.TxtDatasourceStrategy
import java.io.File

@DisplayName("Player Converter Test")
class ConverterTests {

    @Test
    fun `should convert from csv with players to list of string`() {
        val csvConverter = CsvDatasourceStrategy()
        val dir = File("src/test/resources/testFiles")
        val listFiles = dir.listFiles { dir, name -> name.endsWith(".csv") }
        val listOfLines = csvConverter.readDatasource(listFiles[0])
        assertThat(listOfLines.size).isEqualTo(listFiles[0].readLines().size)
        assertThat(listOfLines[0].size).isEqualTo(3)
    }

    @Test
    fun `should convert from txt with game to list of string`() {
        val txtConverter = TxtDatasourceStrategy()
        val dir = File("src/test/resources/testFiles")
        val listFiles = dir.listFiles { dir, name -> name.endsWith(".txt") }
        val listOfLines  = txtConverter.readDatasource(listFiles[0])
        assertThat(listOfLines.size).isEqualTo(listFiles[0].readLines().size)
        assertThat(listOfLines[0].size).isEqualTo(4)
    }
}