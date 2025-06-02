package com.jmg.treasurehunt.services.impl;

import com.jmg.treasurehunt.model.EtatLineModel;
import com.jmg.treasurehunt.services.LineValidator;
import com.jmg.treasurehunt.tools.TreasureHuntEnum;

import java.text.MessageFormat;

public class LineValidatorTypeA implements LineValidator {
    @Override
    public EtatLineModel validate(String[] parts) {
        if (parts.length != 6) {
            return new EtatLineModel(false, "Line type A has incorrect length");
        }

        String msg = "";
        for (int i = 2; i < 4; i++) {
            try {
                Integer.parseInt(parts[i]);
            } catch (NumberFormatException e) {
                if (msg.isEmpty()) {
                    msg = MessageFormat.format("Line type A: field at index {0} is not a number (value: {1})", i, parts[i]);
                } else {
                    msg = msg.concat(MessageFormat.format(" and field at index {0} is not a number (value: {1})", i, parts[i]));
                }
            }
        }
        if (!TreasureHuntEnum.isCompassRose(parts[4])) {
            if (msg.isEmpty()) {
                msg = MessageFormat.format("Line type A: field at index 4 is Invalid cardinal point (value: {0})", parts[4]);
            } else {
                msg = msg.concat(MessageFormat.format(" and field at index 4 is Invalid cardinal point (value: {0})", parts[4]));
            }
        }
        String[] direction = parts[5].split("");
        for (String move : direction) {
            if (!TreasureHuntEnum.isMoveAutorised(move)) {
                if (msg.isEmpty()) {
                    msg = MessageFormat.format("Line type A: field at index 5 is Invalid move point (value: {0})", move);
                } else {
                    msg = msg.concat(MessageFormat.format(" and field at index 5 is Invalid move point (value: {0})", move));
                }
            }
        }
        return new EtatLineModel(msg.isBlank(), msg);
    }

    @Override
    public boolean supports(String lineType) {
        return "A".equalsIgnoreCase(lineType);
    }
}
