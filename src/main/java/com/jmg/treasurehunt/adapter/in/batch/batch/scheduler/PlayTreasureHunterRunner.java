package com.jmg.treasurehunt.adapter.in.batch.batch.scheduler;

import com.jmg.treasurehunt.application.tools.DateFormatEnum;
import lombok.extern.slf4j.Slf4j;
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
import java.time.format.DateTimeFormatter;

/**
 * Runner of batch PlayTreasureHunterRunner
 *  PlayTreasureHunter is running every minute
 *
 * @Autor: GADEAUD Jean-Michel
 */
@Slf4j
@Component
public class PlayTreasureHunterRunner {
    @Autowired
    private JobLauncher jobLauncher;

    private final Job job;

    public PlayTreasureHunterRunner(@Qualifier("playTreasureHunterJob") Job playTreasureHunterJob) {
        this.job = playTreasureHunterJob;
    }

    @Scheduled(cron = "0 */1 * * * *") // every min
    public void launchJob() {
        try {
            DateTimeFormatter dtf = DateFormatEnum.ISO_DATE_TIME.getFormatter();
            LocalDateTime now = LocalDateTime.now();
            log.info("PlayTreasureHunter start !!! {}", now.format(dtf));
            JobParameters params = new JobParametersBuilder()
                    .addLocalDateTime("run.date", now) // pour garantir l'unicité des executions
                    .toJobParameters();

            JobExecution execution = jobLauncher.run(job, params);
            log.info("PlayTreasureHunter end !!! {}", LocalDateTime.now().format(dtf));

        } catch (Exception e) {
            throw new RuntimeException("batch TreasureHunter not working : " + e.getMessage());
        }
    }
}
