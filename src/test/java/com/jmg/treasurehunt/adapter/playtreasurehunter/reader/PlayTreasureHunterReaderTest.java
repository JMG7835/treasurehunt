package com.jmg.treasurehunt.adapter.playtreasurehunter.reader;

import com.jmg.treasurehunt.adapter.in.batch.batch.playtreasurehunter.reader.PlayTreasureHunterReader;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.File;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
public class PlayTreasureHunterReaderTest {

    private static final String INBOUND = "src/test/resources/playtreasurehunter/inbound/";
    private static final String REGEXFILE = "^treasure.*\\.txt$";


    @Test
    void readFileTest_OK() {
        PlayTreasureHunterReader myReader = new PlayTreasureHunterReader(INBOUND, REGEXFILE);

        File file;
        int count = 0;
        while ((file = myReader.read()) != null) {
            assertThat(file.getName()).matches(REGEXFILE);
            count++;
        }

        assertThat(count).isEqualTo(4);
    }

}