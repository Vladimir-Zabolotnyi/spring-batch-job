package zabolotnyi.springbatchjob

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import zabolotnyi.springbatchjob.player.CsvConverterStrategy
import zabolotnyi.springbatchjob.player.TxtConverterStrategy
import java.io.File

@DisplayName("Player Converter Test")
class ConverterTests {

    @Test
    fun `should convert from csv to player list`() {
        val csvConverter = CsvConverterStrategy()
        val dir = File("src/test/resources/testFiles")
        val listFiles = dir.listFiles { dir, name -> name.endsWith(".csv") }
        val listOfPlayer = csvConverter.convertToEntity(listFiles[0])
        assertThat(listOfPlayer.size).isEqualTo(listFiles[0].readLines().size)
    }

    @Test
    fun `should convert from txt to player list`() {
        val txtConverter = TxtConverterStrategy()
        val dir = File("src/test/resources/testFiles")
        val listFiles = dir.listFiles { dir, name -> name.endsWith(".txt") }
        val listOfPlayer = txtConverter.convertToEntity(listFiles[0])
        assertThat(listOfPlayer.size).isEqualTo(listFiles[0].readLines().size)
    }
}