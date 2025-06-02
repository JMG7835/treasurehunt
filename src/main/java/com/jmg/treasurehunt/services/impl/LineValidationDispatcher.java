package com.jmg.treasurehunt.services.impl;

import com.jmg.treasurehunt.model.EtatLineModel;
import com.jmg.treasurehunt.services.LineValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class LineValidationDispatcher {

    private final List<LineValidator> validators;

    @Autowired
    public LineValidationDispatcher(List<LineValidator> validators) {
        this.validators = validators;
    }

    public EtatLineModel validate(String line) {
        String[] parts = line.split("_");
        String lineType = parts[0].trim();

        return validators.stream()
                .filter(v -> v.supports(lineType))
                .findFirst()
                .map(v -> v.validate(parts))
                .orElse(new EtatLineModel(false,"No validator found for line type: " + lineType));
    }
}
