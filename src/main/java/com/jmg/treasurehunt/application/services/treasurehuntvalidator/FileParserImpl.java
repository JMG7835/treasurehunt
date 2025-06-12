package com.jmg.treasurehunt.application.services.treasurehuntvalidator;

import com.jmg.treasurehunt.domain.model.EtatFileTreasureHuntModel;
import com.jmg.treasurehunt.domain.port.in.validator.FileParser;
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
