package com.jmg.treasurehunt.batch.playtreasurehunter;

import com.jmg.treasurehunt.batch.playtreasurehunter.processor.PlayTreasureHunterProcessor;
import com.jmg.treasurehunt.batch.playtreasurehunter.reader.PlayTreasureHunterReader;
import com.jmg.treasurehunt.batch.playtreasurehunter.writer.PlayTreasureHunterWriter;
import com.jmg.treasurehunt.model.EtatFileTreasureHunt;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
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

/**
 * Config and Job of batch PlayTreasureHunterRunner
 * In application.properties, add if not existe
 *     Value treasure_file.path.inbound for repository of inbound file
 *     Value treasure_file.path.outbound for repository of outbound file
 *     Value treasure_file.path.error for repository of error file
 *     Value treasure_file.regex regex for find file
 *
 * @Autor: GADEAUD Jean-Michel
 */
@Configuration
public class PlayTreasureHunterConfig {

    public static final Logger logger = LoggerFactory.getLogger(PlayTreasureHunterConfig.class);
    @Value("${treasure_file.path.inbound}")
    private String inboundFile;
    @Value("${treasure_file.path.outbound}")
    private String outboundFile;
    @Value("${treasure_file.path.error}")
    private String errorFile;
    @Value("${treasure_file.regex}")
    private String fileRegex;

    @Bean
    public Job playTreasureHunterJob(JobRepository jobRepository, Step myStep) {
        return new JobBuilder("PlayTreasureHunterJob", jobRepository)
                .start(myStep)
                .build();
    }


    @Bean
    public Step myStep(JobRepository jobRepository, PlatformTransactionManager transactionManager) {
        ItemReader<File> reader = new PlayTreasureHunterReader(inboundFile, fileRegex);
        ItemProcessor<File, EtatFileTreasureHunt> processor = new PlayTreasureHunterProcessor();
        ItemWriter<EtatFileTreasureHunt> writer = new PlayTreasureHunterWriter(outboundFile,errorFile);
        return new StepBuilder("myStep", jobRepository)
                .<File, EtatFileTreasureHunt>chunk(5, transactionManager)
                .reader(reader)
                .processor(processor)
                .writer(writer)
                .build();
    }
}