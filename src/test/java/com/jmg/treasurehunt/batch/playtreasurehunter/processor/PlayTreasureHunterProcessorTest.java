package com.jmg.treasurehunt.batch.playtreasurehunter.processor;

import com.jmg.treasurehunt.batch.listener.ArchiveListener;
import com.jmg.treasurehunt.model.EtatFileTreasureHuntModel;
import com.jmg.treasurehunt.model.EtatLineModel;
import com.jmg.treasurehunt.services.TreasureHuntFileServices;
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
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@SpringBootTest
@SpringBatchTest
@TestPropertySource(locations = "classpath:application-test.properties")
public class PlayTreasureHunterProcessorTest {

    @Value("${treasure_file.path.inbound}")
    private String inboundFile;

    @Mock
    private ArchiveListener archiveListener;
    @Mock
    private TreasureHuntFileServices treasureHuntFileServices;
    @InjectMocks
    private PlayTreasureHunterProcessor processor;

    @Test
    void processTest_OK() throws URISyntaxException, IOException {
        File fileOk = new File(inboundFile + "treasure_ok.txt");
        doNothing().when(archiveListener).setLastFile(any());
        when(treasureHuntFileServices.controleLine(any())).thenReturn(new EtatLineModel(true, "no an error"));
        when(treasureHuntFileServices.play(any())).thenReturn(List.of("great"));
        EtatFileTreasureHuntModel result = processor.process(fileOk);

        Assertions.assertNotNull(result);
        assertTrue(result.isOk());
        assertThat("treasure_ok.txt").isEqualTo(result.fileName());
    }


    @Test
    void processTest_KO() throws URISyntaxException, IOException {
        doNothing().when(archiveListener).setLastFile(any());
        when(treasureHuntFileServices.controleLine(any())).thenReturn(new EtatLineModel(false, "error"));

        EtatFileTreasureHuntModel result = processor.process(new File(inboundFile + "treasure_ko.txt"));

        Assertions.assertNotNull(result);
        assertFalse(result.isOk());
        assertThat("treasure_ko.txt").isEqualTo(result.fileName());
        assertThat(8).isEqualTo(result.lines().size());
    }

}
