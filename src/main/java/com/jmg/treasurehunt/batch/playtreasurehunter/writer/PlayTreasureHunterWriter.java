package com.jmg.treasurehunt.batch.playtreasurehunter.writer;

import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PlayTreasureHunterWriter implements ItemWriter<String> {

    @Override
    public void write(List<? extends String> items) {
        items.forEach(System.out::println);

    }
}
