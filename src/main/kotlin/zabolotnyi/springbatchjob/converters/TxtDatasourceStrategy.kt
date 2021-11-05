package zabolotnyi.springbatchjob.converters

import java.io.File
import java.util.regex.Pattern

class TxtDatasourceStrategy : DatasourceStrategy {

    override fun readDatasource(file: File): List<List<String>> = file.run {
        file.readLines().map { it.split(Pattern.compile("\\s+")) }
    }
}