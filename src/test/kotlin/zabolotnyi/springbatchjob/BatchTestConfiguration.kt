package zabolotnyi.springbatchjob

import org.springframework.batch.test.JobLauncherTestUtils
import org.springframework.batch.test.JobRepositoryTestUtils
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class BatchTestConfiguration {

    @Bean
    fun jobLauncherTestUtils(): JobLauncherTestUtils {
        return JobLauncherTestUtils()
    }

    @Bean
    fun jobRepositoryTestUtils(): JobRepositoryTestUtils {
        return JobRepositoryTestUtils()
    }
}