package com.jmg.treasurehunt.batch.playtreasurehunter;

import com.jmg.treasurehunt.batch.playtreasurehunter.processor.PlayTreasureHunterProcessor;
import com.jmg.treasurehunt.batch.playtreasurehunter.reader.PlayTreasureHunterReader;
import com.jmg.treasurehunt.batch.playtreasurehunter.writer.PlayTreasureHunterWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

import java.io.File;


@Configuration
@EnableBatchProcessing
public class PlayTreasureHunterConfig {

    public static final Logger logger = LoggerFactory.getLogger(PlayTreasureHunterConfig.class);
    @Value("${treasure_file.path.inbound}")
    private String inboundFile;
    @Value("${treasure_file.path.outbound}")
    private String outboundFile;
    @Value("${treasure_file.path.error}")
    private String errorFile;
    @Value("${treasure_file.extention}")
    private String pathExtention;

    @Bean
    public Job PlayTreasureHunterJob(JobRepository jobRepository, Step myStep) {
        return new JobBuilder("PlayTreasureHunterJob", jobRepository)
                .start(myStep)
                .build();
    }

    @Bean
    public Step myStep(JobRepository jobRepository, PlatformTransactionManager transactionManager) {
        ItemReader<File> reader = new PlayTreasureHunterReader(inboundFile, pathExtention);
        ItemProcessor<File, String> processor = new PlayTreasureHunterProcessor();
        ItemWriter<String> writer = new PlayTreasureHunterWriter();
        return new StepBuilder("myStep", jobRepository)
                .<File, String>chunk(5, transactionManager)
                .reader(reader)
                .processor(processor)
                .writer(writer)
                .build();
    }
}