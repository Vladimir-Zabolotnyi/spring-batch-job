package zabolotnyi.springbatchjob

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class ImportApplication

fun main(args: Array<String>) {
    runApplication<ImportApplication>(*args)
}

