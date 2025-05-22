package com.jmg.treasurehunt.batch.scheduler;

import com.jmg.treasurehunt.batch.playtreasurehunter.PlayTreasureHunterConfig;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.Locale;

@Component
public class playtreasurehunterRunner {
    @Autowired
    private JobLauncher jobLauncher;

    private final Job job;

    public playtreasurehunterRunner(@Qualifier("playTreasureHunterJob") Job playTreasureHunterJob) {
        this.job = playTreasureHunterJob;
    }

    @Scheduled(cron = "0 */2 * * * *") // every 2 min
    public void launchJob() {
        try {
            JobParameters params = new JobParametersBuilder()
                    .addLocalDateTime("run.date", LocalDateTime.now()) // pour garantir l'unicité des executions
                    .toJobParameters();

            JobExecution execution = jobLauncher.run(job, params);
            System.out.println("Job finished with status: " + execution.getStatus());

        } catch (Exception e) {
            throw new RuntimeException("batch TreasureHunter not working : "+e.getMessage());
        }
    }
}
