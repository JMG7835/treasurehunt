package com.jmg.treasurehunt.services.impl;

import com.jmg.treasurehunt.model.EtatFileTreasureHuntModel;
import com.jmg.treasurehunt.model.EtatLineModel;
import com.jmg.treasurehunt.services.FileParser;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class FileParserImpl implements FileParser {
    private final LineValidationDispatcher dispatcher;

    @Autowired
    public FileParserImpl(LineValidationDispatcher dispatcher) {
        this.dispatcher = dispatcher;
    }

    @Override
    public EtatFileTreasureHuntModel parse(File file) {
        List<String> validLines = new ArrayList<>();
        boolean etatFile = true;

        try (Stream<String> lines = Files.lines(file.toPath())) {
            for (String line : (Iterable<String>) lines.filter(l -> !l.trim().startsWith("#"))::iterator) {
                EtatLineModel etat = dispatcher.validate(line);
                if (!etat.isOk()) {
                    etatFile = false;
                }
                validLines.add(etat.line() != null ? etat.line() : ""); // fallback
            }
        } catch (IOException e) {
            throw new RuntimeException("Failed to parse file: " + file.getName(), e);
        }

        return new EtatFileTreasureHuntModel(etatFile, file.getName(), validLines);
    }
}
