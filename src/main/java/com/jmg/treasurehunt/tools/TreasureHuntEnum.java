package com.jmg.treasurehunt.tools;

import java.util.List;

/**
 * Enum for TreasureHuntFile
 *
 * @Autor: GADEAUD Jean-Michel
 */

public enum TreasureHuntEnum {

    LINE_C("C"),
    LINE_M("M"),
    LINE_T("T"),
    LINE_A("A"),
    LINE_COMMENT("#"),
    NORD("N"),
    SOUTH("S"),
    WEST("W"),
    EAST("E"),
    FRONT("A"),
    LEFT("G"),
    RIGHT("D"),
    ERROR("ERROR");

    private final String pattern;

    TreasureHuntEnum(String pattern) {
        this.pattern = pattern;
    }

    public static TreasureHuntEnum fromPattern(String pattern) {
        for (TreasureHuntEnum treasureHuntEnum : TreasureHuntEnum.values()) {
            if (treasureHuntEnum.getPattern().equalsIgnoreCase(pattern)) {
                return treasureHuntEnum;
            }
        }
        return ERROR;
    }

    public String getPattern() {
        return pattern;
    }

    public static boolean isCompassRose(final String cardinalPoint) {
        return List.of(NORD.pattern, SOUTH.pattern, WEST.pattern, EAST.pattern).contains(cardinalPoint);
    }

    public static boolean isMoveAutorised(final String move) {
        return List.of(FRONT.pattern, LEFT.pattern, RIGHT.pattern).contains(move);
    }

    @Override
    public String toString() {
        return pattern;
    }

}
