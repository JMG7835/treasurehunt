package com.jmg.treasurehunt.adapter.playtreasurehunter.writer;

import com.jmg.treasurehunt.adapter.in.batch.batch.playtreasurehunter.writer.PlayTreasureHunterWriter;
import com.jmg.treasurehunt.domain.model.EtatFileTreasureHuntModel;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.batch.item.Chunk;

import java.io.File;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;


@ExtendWith(MockitoExtension.class)
public class PlayTreasureHunterWriterTest {

    private static final String OUTBOUND = "src/test/resources/playtreasurehunter/outbound/";
    private static final String ERROR = "src/test/resources/playtreasurehunter/error/";


    @BeforeEach
    @AfterEach
    void deleteRepo() {
        File[] filesList = new File(OUTBOUND).listFiles();
        if (filesList != null) {
            for (File file : filesList) {
                if (file.isFile()) {
                    file.delete();
                }
            }
        }
        filesList = new File(ERROR).listFiles();
        if (filesList != null) {
            for (File file : filesList) {
                if (file.isFile()) {
                    file.delete();
                }
            }
        }
    }

    @Test
    public void writTest_OK() {
        PlayTreasureHunterWriter myWriter = new PlayTreasureHunterWriter(OUTBOUND, ERROR);
        EtatFileTreasureHuntModel etatFileTreasureHunt = new EtatFileTreasureHuntModel(true, "toto.txt", List.of("one line"));
        myWriter.write(Chunk.of(etatFileTreasureHunt));
        File[] filesList = new File(OUTBOUND).listFiles();
        if (filesList == null) {
            fail("error folder null");
        } else {
            assertThat(filesList.length).isEqualTo(1);
        }
    }

    @Test
    public void writTest_ERROR() {
        PlayTreasureHunterWriter myWriter = new PlayTreasureHunterWriter(OUTBOUND, ERROR);
        EtatFileTreasureHuntModel etatFileTreasureHunt = new EtatFileTreasureHuntModel(false, "toto.txt", List.of("one line"));
        myWriter.write(Chunk.of(etatFileTreasureHunt));
        File[] filesList = new File(ERROR).listFiles();
        if (filesList == null) {
            fail("error folder null");
        } else {
            assertThat(filesList.length).isEqualTo(1);
        }
    }

    @Test
    public void writTest_no_line() {
        PlayTreasureHunterWriter myWriter = new PlayTreasureHunterWriter(OUTBOUND, ERROR);
        EtatFileTreasureHuntModel etatFileTreasureHunt = new EtatFileTreasureHuntModel(true, "toto.txt", List.of());
        myWriter.write(Chunk.of(etatFileTreasureHunt));
        File[] filesList = new File(OUTBOUND).listFiles();
        if (filesList == null) {
            fail("error folder null");
        } else {
            assertThat(filesList.length).isEqualTo(0);
        }
    }
}
