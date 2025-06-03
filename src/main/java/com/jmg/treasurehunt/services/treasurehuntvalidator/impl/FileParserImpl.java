package com.jmg.treasurehunt.services.treasurehuntvalidator.impl;

import com.jmg.treasurehunt.model.EtatFileTreasureHuntModel;
import com.jmg.treasurehunt.services.treasurehuntvalidator.FileParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class FileParserImpl implements FileParser {
    @Autowired
    private LineValidationDispatcher dispatcher;

    @Override
    public EtatFileTreasureHuntModel parse(List<String> file, String fileName) {
        List<String> validLines = new ArrayList<>();
        file.forEach(line ->
                validLines.add(dispatcher.validate(line))
        );
        boolean isOK = validLines.stream().noneMatch(s -> s != null && !s.trim().isEmpty());
        return new EtatFileTreasureHuntModel(isOK, fileName, validLines);
    }
}
