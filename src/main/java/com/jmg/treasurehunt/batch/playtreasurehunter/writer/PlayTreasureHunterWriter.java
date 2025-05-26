package com.jmg.treasurehunt.batch.playtreasurehunter.writer;

import com.jmg.treasurehunt.model.EtatFileTreasureHunt;
import com.jmg.treasurehunt.tools.DateFormatEnum;
import com.jmg.treasurehunt.tools.TreasureHuntFileTools;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;

/**
 * Writer of batch PlayTreasureHunterRunner
 * Write a file in folder set in "treasure_file.path.outbound" if not error new file name is oldFileName add tretement date else
 * Write a file in folder set in "treasure_file.path.error" if one error is found  new file name is ERROR oldFileName add tretement date
 *
 * @author GADEAUD Jean-MICHEL
 */
@Component
public class PlayTreasureHunterWriter implements ItemWriter<EtatFileTreasureHunt> {

    private final String outboundPath;
    private final String errorPath;

    /**
     * Constructs the writer with the path of the file to write to.
     *
     * @param outboundPath the path of the output  error less file
     * @param errorPath the path of the output error file
     */
    public PlayTreasureHunterWriter(@Value("${treasure_file.path.outbound}") String outboundPath,
                                    @Value("${treasure_file.path.error}") String errorPath) {
        this.outboundPath = outboundPath;
        this.errorPath = errorPath;
    }

    @Override
    public void write(Chunk<? extends EtatFileTreasureHunt> Chunk) {

        Chunk.forEach(etatFileTreasureHunt -> {
            String outputFilePath;
            File outputFile;
            String oldName = etatFileTreasureHunt.getFileName();
            String[] nameAndExtention = oldName.split("\\.");
            String newNameFile = nameAndExtention[0] +
                    TreasureHuntFileTools.UNDERSCORT +
                    LocalDateTime.now().format(DateFormatEnum.FOR_FILE.getFormatter()) +
                    TreasureHuntFileTools.DOT + nameAndExtention[1];
            if (etatFileTreasureHunt.isOk()) {
                outputFilePath = this.outboundPath + newNameFile;
            } else {
                outputFilePath = this.errorPath + "ERROR_" + newNameFile;
            }
            outputFile = new File(outputFilePath);
            etatFileTreasureHunt.getLines().forEach(line -> {
                try (BufferedWriter writer = new BufferedWriter(new FileWriter(outputFile, true))) {
                    writer.write(line);
                    writer.newLine();
                } catch (IOException e) {
                    throw new RuntimeException("Error writing to file: " + outputFile.getAbsolutePath(), e);
                }
            });

        });
    }

}
