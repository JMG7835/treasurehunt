package com.jmg.treasurehunt.batch.playtreasurehunter.processor;

import com.jmg.treasurehunt.batch.listener.ArchiveListener;
import com.jmg.treasurehunt.model.EtatFileTreasureHuntModel;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.batch.test.context.SpringBatchTest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;

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
    void processTest_OK() throws URISyntaxException, IOException {
        File fileOk = new File(inboundFile + "treasure_ok.txt");
        Path path = Paths.get(getClass()
                .getClassLoader()
                .getResource("playtreasurehunter/result/result_treasure_ok.txt")
                .toURI());
        List<String> resultFileOk;
        try (Stream<String> lines = Files.lines(path)) {
            resultFileOk = lines.toList();
        }
        doNothing().when(archiveListener).setLastFile(any());

        EtatFileTreasureHuntModel result = processor.process(fileOk);

        Assertions.assertNotNull(result);
        assertTrue(result.isOk());
        assertThat(resultFileOk.size()).isEqualTo(result.lines().size());
        assertThat(result.lines()).containsExactlyElementsOf(resultFileOk);
    }


    @Test
    void processTest_KO() throws URISyntaxException, IOException {
        File fileKO = new File(inboundFile + "treasure_ko.txt");
        Path path = Paths.get(getClass()
                .getClassLoader()
                .getResource("playtreasurehunter/result/result_treasure_ko.txt")
                .toURI());
        List<String> resultFileko;
        try (Stream<String> lines = Files.lines(path)) {
            resultFileko = lines.toList();
        }
        doNothing().when(archiveListener).setLastFile(any());

        EtatFileTreasureHuntModel result = processor.process(fileKO);

        Assertions.assertNotNull(result);
        assertFalse(result.isOk());
        assertThat(resultFileko.size()).isEqualTo(result.lines().size());
        assertThat(result.lines()).containsExactlyElementsOf(resultFileko);
    }

}
