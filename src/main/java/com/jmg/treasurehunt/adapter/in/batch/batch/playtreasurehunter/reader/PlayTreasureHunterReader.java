package com.jmg.treasurehunt.adapter.in.batch.batch.playtreasurehunter.reader;

import org.springframework.batch.item.ItemReader;
import org.springframework.beans.factory.annotation.Value;

import java.io.File;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

/**
 * Reader of batch PlayTreasureHunterRunner
 * Read a folder set in "treasure_file.path.inbound" and return a files with matche in regex
 *
 * @author  GADEAUD Jean-MICHEL
 */
public class PlayTreasureHunterReader implements ItemReader<File> {

    private final Iterator<File> fileIterator;

    /**
     * Constructs the reader with a target directory and a regex pattern
     * to filter the files to be processed.
     *
     * @param directoryPath the path to the directory containing files
     * @param regexFile     the regex pattern to match filenames
     */
    public PlayTreasureHunterReader(@Value("${treasure_file.path.inbound}") String directoryPath,@Value("${treasure_file.regex}") String regexFile) {
        File folder = new File(directoryPath);
        File[] files = folder.listFiles((dir, name) -> name.matches(regexFile));
        List<File> fileList = files != null ? Arrays.asList(files) : List.of();
        this.fileIterator = fileList.iterator();
    }

    @Override
    public File read() {
        return fileIterator.hasNext() ? fileIterator.next() : null;
    }

}