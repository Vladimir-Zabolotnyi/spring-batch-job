package zabolotnyi.springbatchjob.converters

import java.io.File

class CsvDatasourceStrategy : DatasourceStrategy {

    override fun readDatasource(file: File): List<List<String>> = file.run {
        file.readLines().map { it.split(",") }
    }
}