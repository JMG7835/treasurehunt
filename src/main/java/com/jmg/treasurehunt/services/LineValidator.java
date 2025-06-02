package com.jmg.treasurehunt.services;

import com.jmg.treasurehunt.model.EtatLineModel;

public interface LineValidator {
    EtatLineModel validate(String[] line);
    boolean supports(String lineType);
}
