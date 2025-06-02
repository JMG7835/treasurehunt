package com.jmg.treasurehunt.batch.playtreasurehunter.processor;

import com.jmg.treasurehunt.batch.listener.ArchiveListener;
import com.jmg.treasurehunt.model.EtatFileTreasureHuntModel;
import com.jmg.treasurehunt.model.EtatLineModel;
import com.jmg.treasurehunt.services.FileArchiver;
import com.jmg.treasurehunt.services.FileParser;
import com.jmg.treasurehunt.services.TreasureHuntFileServices;
import com.jmg.treasurehunt.tools.TreasureHuntEnum;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.text.MessageFormat;
import java.util.List;
import java.util.stream.Stream;

/**
 * Processor of batch PlayTreasureHunterRunner
 * For each file, check if line is corrected and play the game and return the result
 *
 * @author GADEAUD Jean-MICHEL
 */
@Component
public class PlayTreasureHunterProcessor implements ItemProcessor<File, EtatFileTreasureHuntModel> {

    private final FileParser parser;
    private final FileArchiver archiver;

    @Autowired
    public PlayTreasureHunterProcessor(FileParser parser, FileArchiver archiver) {
        this.parser = parser;
        this.archiver = archiver;
    }

    @Override
    public EtatFileTreasureHuntModel process(File file) {
        if (!file.canRead()) {
            throw new RuntimeException(MessageFormat.format("Can't read file: {0}", file.getName()));
        }
        EtatFileTreasureHuntModel result = parser.parse(file);
        archiver.archive(file);
        return result;
    }
}
