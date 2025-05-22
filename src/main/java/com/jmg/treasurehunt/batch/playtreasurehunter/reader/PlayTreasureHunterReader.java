package com.jmg.treasurehunt.batch.playtreasurehunter.reader;

import org.springframework.batch.item.ItemReader;

import java.io.File;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public class PlayTreasureHunterReader implements ItemReader<File> {

    private final Iterator<File> fileIterator;

    public PlayTreasureHunterReader(String directoryPath, String extension) {
        File folder = new File(directoryPath);
        File[] files = folder.listFiles((dir, name) -> name.endsWith(extension));
        List<File> fileList = files != null ? Arrays.asList(files) : List.of();
        this.fileIterator = fileList.iterator();
    }

    @Override
    public File read() {
        return fileIterator.hasNext() ? fileIterator.next() : null;
    }

}