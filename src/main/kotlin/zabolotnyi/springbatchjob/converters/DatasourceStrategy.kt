package zabolotnyi.springbatchjob.converters

import java.io.File

interface DatasourceStrategy {

    fun readDatasource(file: File): List<List<String>>
}