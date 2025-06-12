package com.jmg.treasurehunt.application.services.treasurehuntvalidator;

import com.jmg.treasurehunt.domain.port.in.validator.LineValidator;
import org.springframework.stereotype.Component;

import java.util.List;

import static com.jmg.treasurehunt.application.utils.FilesUtils.HYPHEN;
import static com.jmg.treasurehunt.application.utils.FilesUtils.REGEX_SPACE;
import static com.jmg.treasurehunt.application.utils.FilesUtils.VOID;

@Component
public class LineValidationDispatcher {

    private final List<LineValidator> validators;

    public LineValidationDispatcher(List<LineValidator> validators) {
        this.validators = validators;
    }


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
