package zabolotnyi.springbatchjob.player

import java.io.File

interface ConvertStrategy {

    fun convertToEntity(file: File): List<Player>
}