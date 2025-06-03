package com.jmg.treasurehunt.batch.playtreasurehunter.processor;

import com.jmg.treasurehunt.model.EtatFileTreasureHuntModel;
import com.jmg.treasurehunt.services.treasurehuntaction.TreasureGame;
import com.jmg.treasurehunt.services.treasurehuntvalidator.FileArchiver;
import com.jmg.treasurehunt.services.treasurehuntvalidator.FileParser;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.text.MessageFormat;
import java.util.ArrayList;
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

    @Autowired
    private FileParser parser;
    @Autowired
    private FileArchiver archiver;
    @Autowired
    private TreasureGame game;


    @Override
    public EtatFileTreasureHuntModel process(File file) {
        String fileName = file.getName();
        if (!file.canRead()) {
            throw new RuntimeException(MessageFormat.format("Can't read file: {0}", fileName));
        }
        List<String> fileLines;
        try (Stream<String> lines = Files.lines(file.toPath())) {
            fileLines = lines.filter(l -> !l.trim().startsWith("#"))
                    .toList();
        } catch (IOException e) {
            throw new RuntimeException("Failed to parse file: " + fileName, e);
        }
        EtatFileTreasureHuntModel result = parser.parse(fileLines, fileName);
        archiver.archive(file);
        if (!result.isOk()) {
            return result;
        }
        List<String> resultLines = game.play(fileLines);
        return new EtatFileTreasureHuntModel(true, file.getName(),
                resultLines);
    }
}
