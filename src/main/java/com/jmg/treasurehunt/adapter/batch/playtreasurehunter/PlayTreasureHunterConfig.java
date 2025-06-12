package com.jmg.treasurehunt.adapter.batch.playtreasurehunter;

import com.jmg.treasurehunt.adapter.batch.listener.ArchiveListener;
import com.jmg.treasurehunt.adapter.batch.playtreasurehunter.processor.PlayTreasureHunterProcessor;
import com.jmg.treasurehunt.adapter.batch.playtreasurehunter.reader.PlayTreasureHunterReader;
import com.jmg.treasurehunt.adapter.batch.playtreasurehunter.writer.PlayTreasureHunterWriter;
import com.jmg.treasurehunt.domain.model.EtatFileTreasureHuntModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

import java.io.File;

/**
 * Config and Job of batch PlayTreasureHunterRunner
 * In application.properties, add if not existe
 * Value treasure_file.path.inbound for repository of inbound file
 * Value treasure_file.path.outbound for repository of outbound file
 * Value treasure_file.path.error for repository of error file
 * Value treasure_file.regex regex for find file
 *
 * @Autor: GADEAUD Jean-Michel
 */
@Configuration
public class PlayTreasureHunterConfig {

    public static final Logger logger = LoggerFactory.getLogger(PlayTreasureHunterConfig.class);
    @Value("${treasure_file.path.inbound}")
    private String inboundPatch;
    @Value("${treasure_file.path.outbound}")
    private String outboundPatch;
    @Value("${treasure_file.path.error}")
    private String errorPatch;
    @Value("${treasure_file.regex}")
    private String fileRegex;

    @Autowired
    private JobRepository jobRepository;
    @Autowired
    private PlatformTransactionManager transactionManager;
    @Autowired
    private ArchiveListener fileArchiverListener;

    @Bean
    public Job playTreasureHunterJob(JobRepository jobRepository, Step processing) {
        return new JobBuilder("PlayTreasureHunterJob", jobRepository)
                .start(processing)
                .build();
    }

    @Bean
    public PlayTreasureHunterReader playTreasureHunterReader() {
        return new PlayTreasureHunterReader(inboundPatch, fileRegex);
    }

    @Bean
    public PlayTreasureHunterWriter playTreasureHunterWriter() {
        return new PlayTreasureHunterWriter(outboundPatch, errorPatch);
    }

    @Bean
    public Step processing(PlayTreasureHunterReader reader,
                           PlayTreasureHunterProcessor processor,
                           PlayTreasureHunterWriter writer) {
        return new StepBuilder("processing", jobRepository)
                .<File, EtatFileTreasureHuntModel>chunk(5, transactionManager)
                .reader(reader)
                .processor(processor)
                .writer(writer)
                .listener(fileArchiverListener)
                .build();
    }
}