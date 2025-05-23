package com.jmg.treasurehunt.batch.playtreasurehunter.writer;

import com.jmg.treasurehunt.model.EtatFileTreasureHunt;
import com.jmg.treasurehunt.tools.DateFormatEnum;
import com.jmg.treasurehunt.tools.TreasureHuntFileTools;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.test.context.SpringBatchTest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.stereotype.Component;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;

@SpringBootTest
@SpringBatchTest
public class PlayTreasureHunterWriterTest implements ItemWriter<EtatFileTreasureHunt> {

    private String outboundFile;
    private String errorFile;

    /**
     * Constructs the writer with the path of the file to write to.
     *
     * @param outboundFile the path of the output  error less file
     * @param outboundFile the path of the output error file
     */
    public PlayTreasureHunterWriterTest(@Value("${treasure_file.path.outbound}") String outboundFile, @Value("${treasure_file.path.error}") String errorFile) {
        this.outboundFile = outboundFile;
        this.errorFile = errorFile;
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
                    LocalDateTime.now().format(DateFormatEnum.ISO_DATE_TIME.getFormatter()) +
                    TreasureHuntFileTools.DOT + nameAndExtention[1];
            if (etatFileTreasureHunt.isOk()) {
                outputFilePath = this.outboundFile + newNameFile;
            } else {
                outputFilePath = this.errorFile + "ERROR" + newNameFile;
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
