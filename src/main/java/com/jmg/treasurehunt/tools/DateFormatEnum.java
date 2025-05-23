package com.jmg.treasurehunt.tools;

import java.time.format.DateTimeFormatter;

/**
 * Enum of format date pattern
 *
 * @Autor: GADEAUD Jean-Michel
 */
public enum DateFormatEnum {
    ISO_LOCAL("yyyy-MM-dd"),
    ISO_DATE_TIME("yyyy-MM-dd'T'HH:mm:ss");

    private final String pattern;
    private final DateTimeFormatter formatter;

    DateFormatEnum(String pattern) {
        this.pattern = pattern;
        this.formatter = DateTimeFormatter.ofPattern(pattern);
    }

    public DateTimeFormatter getFormatter() {
        return formatter;
    }

    public String getPattern() {
        return pattern;
    }

    @Override
    public String toString() {
        return pattern;
    }
}
