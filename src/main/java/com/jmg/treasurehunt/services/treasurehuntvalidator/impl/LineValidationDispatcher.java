package com.jmg.treasurehunt.services.treasurehuntvalidator.impl;

import com.jmg.treasurehunt.services.treasurehuntvalidator.LineValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

import static com.jmg.treasurehunt.utils.FilesUtils.*;

@Component
public class LineValidationDispatcher {

    @Autowired
    private List<LineValidator> validators;


    public String validate(String line) {
        String[] parts = line.replaceAll(REGEX_SPACE, VOID).split(HYPHEN);
        String lineType = parts[0];

        return validators.stream()
                .filter(v -> v.supports(lineType))
                .findFirst()
                .map(v -> v.validate(parts))
                .orElse("No validator found for line type: " + lineType);
    }
}
