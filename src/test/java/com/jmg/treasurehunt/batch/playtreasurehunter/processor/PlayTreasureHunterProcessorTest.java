package com.jmg.treasurehunt.batch.playtreasurehunter.processor;

import com.jmg.treasurehunt.model.EtatFileTreasureHunt;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Processor of batch PlayTreasureHunterRunner
 * For each file, check if line is corrected and play the game and return the result
 *
 * @author GADEAUD Jean-MICHEL
 */
@Component
public class PlayTreasureHunterProcessorTest implements ItemProcessor<File, EtatFileTreasureHunt> {

    @Override
    public EtatFileTreasureHunt process(File file) {
        if (!file.canRead()) {
            throw new RuntimeException(MessageFormat.format("Can't read file : {0}", file.getName()));
        }

        List<String> newLine = new ArrayList<>();

        try {
            Files.lines(file.toPath()).forEach(line -> {
                newLine.add(line);
            });
            return new EtatFileTreasureHunt(true, file.getName(), newLine);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
