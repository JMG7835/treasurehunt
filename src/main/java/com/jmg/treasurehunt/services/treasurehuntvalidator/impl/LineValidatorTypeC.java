package com.jmg.treasurehunt.services.treasurehuntvalidator.impl;

import com.jmg.treasurehunt.services.treasurehuntvalidator.LineValidator;
import org.springframework.stereotype.Component;

import java.text.MessageFormat;

@Component
public class LineValidatorTypeC implements LineValidator {
    @Override
    public String validate(String[] parts) {
        if (parts.length != 3) {
            return "Line type C has incorrect length";
        }

        String msg = "";
        for (int i = 1; i < parts.length; i++) {
            try {
                Integer.parseInt(parts[i].trim());
            } catch (NumberFormatException e) {
                if (msg.isEmpty()) {
                    msg = MessageFormat.format("Line type C: field at index {0} is not a number (value: {1})", i, parts[i]);
                } else {
                    msg = msg.concat(MessageFormat.format(" and field at index {0} is not a number (value: {1})", i, parts[i]));
                }
            }
        }

        return msg;
    }

    @Override
    public boolean supports(String lineType) {
        return "C".equalsIgnoreCase(lineType);
    }
}
