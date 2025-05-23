package com.jmg.treasurehunt.batch.playtreasurehunter.reader;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.batch.test.context.SpringBatchTest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import java.io.File;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
@SpringBatchTest
@TestPropertySource(locations = "classpath:application-test.properties")
public class PlayTreasureHunterReaderTest {
    @Value("${treasure_file.path.inbound}")
    private String inboundFile;

    @Value("${treasure_file.regex}")
    private String regexFile;


    @Test
    void readFileTest_OK() {
        PlayTreasureHunterReader myReader = new PlayTreasureHunterReader(inboundFile+"/1",regexFile);

        File file;
        int count = 0;
        while ((file = myReader.read()) != null) {
            assertThat(file.getName()).matches(regexFile);
            count++;
        }

        assertThat(count).isEqualTo(2);
    }

}