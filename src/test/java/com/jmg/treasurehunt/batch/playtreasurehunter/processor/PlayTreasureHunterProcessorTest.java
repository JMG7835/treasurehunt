package com.jmg.treasurehunt.batch.playtreasurehunter.processor;

import com.jmg.treasurehunt.batch.listener.ArchiveListener;
import com.jmg.treasurehunt.model.EtatFileTreasureHunt;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.batch.test.context.SpringBatchTest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URISyntaxException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;

@SpringBootTest
@SpringBatchTest
@TestPropertySource(locations = "classpath:application-test.properties")
public class PlayTreasureHunterProcessorTest {

    @Value("${treasure_file.path.inbound}")
    private String inboundFile;

    @Mock
    private ArchiveListener archiveListener;
    @InjectMocks
    private PlayTreasureHunterProcessor processor;

    @Test
    void processTest_OK() throws URISyntaxException {
        File fileOk = new File(inboundFile+"treasure_ok.txt");
        File resultFileOk = new File(getClass().getClassLoader().getResource("playtreasurehunter/result/result_treasure_ok.txt").toURI());
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileOk))) {
            writer.write("Ligne de test dans le fichier");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        doNothing().when(archiveListener).setLastFile(any());

        EtatFileTreasureHunt result = processor.process(fileOk);

        // Assert
        assertThat(result).isNotNull();

        verify(archiveListener).setLastFile(fileOk);
    }


    @Test
    void processTest_KO() throws URISyntaxException {
        File fileKO = new File(inboundFile+"treasure_ok.txt");
        File resultFileKO = new File(getClass().getClassLoader().getResource("playtreasurehunter/result/result_treasure_ko.txt").toURI());
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileKO))) {
            writer.write("Ligne de test dans le fichier");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        doNothing().when(archiveListener).setLastFile(any());

        EtatFileTreasureHunt result = processor.process(fileKO);

        // Assert
        assertThat(result).isNotNull();

        verify(archiveListener).setLastFile(fileKO); // s'assurer que le listener est informé
    }

}
