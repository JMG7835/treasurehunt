package com.jmg.treasurehunt.adapter.playtreasurehunter;

import org.junit.jupiter.api.Test;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.test.JobLauncherTestUtils;
import org.springframework.batch.test.context.SpringBatchTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@SpringBatchTest
public class PlayTreasureHunterConfigTest {
    @Autowired
    private JobLauncherTestUtils jobLauncherTestUtils;

    @Autowired
    private Job job;

    public PlayTreasureHunterConfigTest(@Qualifier("playTreasureHunterJob") Job playTreasureHunterJob) {
        this.job = playTreasureHunterJob;
    }

    @Test
    void testJobExecution() throws Exception {
        jobLauncherTestUtils.setJob(job);
        JobExecution execution = jobLauncherTestUtils.launchJob();

        assertThat(execution.getExitStatus().getExitCode()).isEqualTo("COMPLETED");
    }
}
