package com.jmg.treasurehunt.batch.playtreasurehunter.writer;

import com.jmg.treasurehunt.model.EtatFileTreasureHunt;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.test.context.SpringBatchTest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import java.io.File;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;

@SpringBootTest
@SpringBatchTest
@TestPropertySource(locations = "classpath:application-test.properties")
public class PlayTreasureHunterWriterTest {

    @Value("${treasure_file.path.outbound}")
    private String outboundPatch;

    @Value("${treasure_file.path.error}")
    private String errorPath;

    @BeforeEach
    void deleteRepo() {
        File[] filesList = new File(outboundPatch).listFiles();
        if (filesList != null) {
            for (File file : filesList) {
                if (file.isFile()) {
                    file.delete();
                }
            }
        }
        filesList = new File(errorPath).listFiles();
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
        PlayTreasureHunterWriter myWriter = new PlayTreasureHunterWriter(outboundPatch, errorPath);
        EtatFileTreasureHunt etatFileTreasureHunt = new EtatFileTreasureHunt(true, "toto.txt", List.of("one line"));
        myWriter.write(Chunk.of(etatFileTreasureHunt));
        File[] filesList = new File(outboundPatch).listFiles();
        if (filesList == null) {
            fail("error folder null");
        } else {
            assertThat(filesList.length).isEqualTo(1);
        }
    }

    @Test
    public void writTest_ERROR() {
        PlayTreasureHunterWriter myWriter = new PlayTreasureHunterWriter(outboundPatch, errorPath);
        EtatFileTreasureHunt etatFileTreasureHunt = new EtatFileTreasureHunt(false, "toto.txt", List.of("one line"));
        myWriter.write(Chunk.of(etatFileTreasureHunt));
        File[] filesList = new File(errorPath).listFiles();
        if (filesList == null) {
            fail("error folder null");
        } else {
            assertThat(filesList.length).isEqualTo(1);
        }
    }

    @Test
    public void writTest_no_line() {
        PlayTreasureHunterWriter myWriter = new PlayTreasureHunterWriter(outboundPatch, errorPath);
        EtatFileTreasureHunt etatFileTreasureHunt = new EtatFileTreasureHunt(true, "toto.txt", List.of());
        myWriter.write(Chunk.of(etatFileTreasureHunt));
        File[] filesList = new File(outboundPatch).listFiles();
        if (filesList == null) {
            fail("error folder null");
        } else {
            assertThat(filesList.length).isEqualTo(0);
        }
    }
}
