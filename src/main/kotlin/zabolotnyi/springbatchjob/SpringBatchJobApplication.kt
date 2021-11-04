package zabolotnyi.springbatchjob

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication()
class SpringBatchJobApplication

fun main(args: Array<String>) {
    runApplication<SpringBatchJobApplication>(*args)
}
