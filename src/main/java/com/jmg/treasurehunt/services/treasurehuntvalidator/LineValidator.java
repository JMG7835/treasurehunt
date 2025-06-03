package com.jmg.treasurehunt.services.treasurehuntvalidator;


public interface LineValidator {
    String validate(String[] line);
    boolean supports(String lineType);
}
