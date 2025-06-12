package com.jmg.treasurehunt.adapter.playtreasurehunter.processor;

import com.jmg.treasurehunt.adapter.in.batch.batch.playtreasurehunter.processor.PlayTreasureHunterProcessor;
import com.jmg.treasurehunt.domain.model.EtatFileTreasureHuntModel;
import com.jmg.treasurehunt.domain.port.in.action.TreasureGame;
import com.jmg.treasurehunt.domain.port.in.validator.FileArchiver;
import com.jmg.treasurehunt.domain.port.in.validator.FileParser;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class PlayTreasureHunterProcessorTest {

    private static final String FILE_KO = "treasure_ko.txt";
    public static final String FILE_OK = "treasure_ok.txt";
    private static final String INBOUND = "src/test/resources/playtreasurehunter/inbound/";

    @Mock
    private FileParser parser;
    @Mock
    private FileArchiver archiver;
    @Mock
    private TreasureGame game;
    @InjectMocks
    private PlayTreasureHunterProcessor processor;


    @Test
    void processTest_OK() throws URISyntaxException, IOException {
        doNothing().when(archiver).archive(any());
        when(parser.parse(anyList(), any())).thenReturn(new EtatFileTreasureHuntModel(true, FILE_OK, List.of("no an error")));
        when(game.play(anyList())).thenReturn(List.of("great"));
        EtatFileTreasureHuntModel result = processor.process(new File(INBOUND + FILE_OK));

        Assertions.assertNotNull(result);
        assertTrue(result.isOk());
        assertThat(FILE_OK).isEqualTo(result.fileName());
    }


    @Test
    void processTest_KO() throws URISyntaxException, IOException {
        doNothing().when(archiver).archive(any());
        when(parser.parse(anyList(), any())).thenReturn(new EtatFileTreasureHuntModel(false, FILE_KO, List.of("error")));

        EtatFileTreasureHuntModel result = processor.process(new File(INBOUND + FILE_KO));

        Assertions.assertNotNull(result);
        assertFalse(result.isOk());
        assertThat(FILE_KO).isEqualTo(result.fileName());
        assertThat(1).isEqualTo(result.lines().size());
    }

}
