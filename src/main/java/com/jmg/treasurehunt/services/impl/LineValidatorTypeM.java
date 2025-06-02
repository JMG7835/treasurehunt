package com.jmg.treasurehunt.services.impl;

import com.jmg.treasurehunt.model.EtatLineModel;
import com.jmg.treasurehunt.services.LineValidator;

import java.text.MessageFormat;

public class LineValidatorTypeM implements LineValidator {
    @Override
    public EtatLineModel validate(String[] parts) {
        if (parts.length != 3) {
            return new EtatLineModel(false,"Line type M has incorrect length");
        }

        String msg ="";
        for (int i = 1; i < parts.length; i++) {
            try {
                Integer.parseInt(parts[i]);
            } catch (NumberFormatException e) {
                if (msg.isEmpty()) {
                    msg = MessageFormat.format("Line type M: field at index {0} is not a number (value: {1})", i, parts[i]);
                } else {
                    msg = msg.concat(MessageFormat.format(" and field at index {0} is not a number (value: {1})", i, parts[i]));
                }
            }
        }
        return new EtatLineModel(msg.isBlank(),msg);

    }
    @Override
    public boolean supports(String lineType) {
        return "M".equalsIgnoreCase(lineType);
    }
}
