package zabolotnyi.springbatchjob

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.batch.core.ExitStatus
import org.springframework.batch.core.JobExecution
import org.springframework.batch.test.JobLauncherTestUtils
import org.springframework.batch.test.JobRepositoryTestUtils
import org.springframework.batch.test.context.SpringBatchTest
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.test.context.ContextConfiguration


@SpringBatchTest
@ContextConfiguration(classes = [BatchConfiguration::class,BatchTestConfiguration::class])
class BatchJobTest @Autowired constructor(
    private val jobLauncherTestUtils: JobLauncherTestUtils,
    private val jobRepositoryTestUtils: JobRepositoryTestUtils,
) {
    @BeforeEach
    fun setUp() {
        this.jobRepositoryTestUtils.removeJobExecutions()
    }

    @Test
    fun `should import player from csv to db`() {

        // given
        val jobParameters = jobLauncherTestUtils.uniqueJobParameters

        // when
        val jobExecution: JobExecution = jobLauncherTestUtils.launchJob(jobParameters)

        // then
        assertThat(jobExecution.exitStatus).isEqualTo(ExitStatus.COMPLETED)
    }

}