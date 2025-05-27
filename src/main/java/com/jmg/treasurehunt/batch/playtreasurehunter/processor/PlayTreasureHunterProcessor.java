package com.jmg.treasurehunt.batch.playtreasurehunter.processor;

import com.jmg.treasurehunt.batch.listener.ArchiveListener;
import com.jmg.treasurehunt.model.EtatFileTreasureHuntModel;
import com.jmg.treasurehunt.model.EtatLineModel;
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

    @Autowired
    private ArchiveListener archiveListener;

    @Autowired
    private TreasureHuntFileServices treasureHuntFileServices;


    @Override
    public EtatFileTreasureHuntModel process(File file) {
        if (!file.canRead()) {
            throw new RuntimeException(MessageFormat.format("Can't read file: {0}", file.getName()));
        }
        this.archiveListener.setLastFile(file);

        Stream<String> lines;
        //controle of file
        try (Stream<String> sLines = Files.lines(file.toPath())) {
            lines =  sLines.filter(line -> !line.startsWith(TreasureHuntEnum.LINE_COMMENT.getPattern()));
            List<EtatLineModel> etatLines = lines
                    .map(TreasureHuntFileServices::controleLine)
                    .toList();
            //file KO
            if (!etatLines.stream().allMatch(EtatLineModel::isOk)) {
                return new EtatFileTreasureHuntModel(false, file.getName(),
                        etatLines.stream().map(EtatLineModel::line).toList());
            }
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }

        //game
        List<String> resultLines = treasureHuntFileServices.play(lines);
        return new EtatFileTreasureHuntModel(true, file.getName(),
                resultLines);
    }
}
