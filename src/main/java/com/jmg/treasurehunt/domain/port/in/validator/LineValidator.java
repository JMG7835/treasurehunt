package com.jmg.treasurehunt.domain.port.in.validator;


public interface LineValidator {
    String validate(String[] line);
    boolean supports(String lineType);
}
