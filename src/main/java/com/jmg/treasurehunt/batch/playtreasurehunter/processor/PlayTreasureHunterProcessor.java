package com.jmg.treasurehunt.batch.playtreasurehunter.processor;

import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.stream.Collectors;

@Component
public class PlayTreasureHunterProcessor implements ItemProcessor<File, String> {

    @Override
    public String process(File file) {
        try {

            return Files.lines(file.toPath())
                    .filter(line -> line.matches("^ligne-.*$"))
                    .collect(Collectors.joining("\n"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
