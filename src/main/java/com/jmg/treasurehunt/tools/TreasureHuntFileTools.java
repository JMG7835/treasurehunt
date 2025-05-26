package com.jmg.treasurehunt.tools;

import com.jmg.treasurehunt.model.EtatLineModel;

import java.text.MessageFormat;
import java.util.List;

/**
 * Tools for TreasureHuntFile
 *
 * @Autor: GADEAUD Jean-Michel
 */

public class TreasureHuntFileTools {
    public static final String UNDERSCORE = "_";
    public static final String HYPHEN = "-";
    public static final String DOT = ".";
    public static final String LINE_C = "C";
    public static final String LINE_M = "M";
    public static final String LINE_T = "T";
    public static final String LINE_A = "A";
    public static final String LINE_COMMENT = "#";
    public static final String NORD = "N";
    public static final String SOUTH = "S";
    public static final String WEST = "W";
    public static final String EAST = "E";
    public static final List<String> COMPASS_ROSE = List.of(NORD, SOUTH, WEST, EAST);
    public static final String FRONT = "A";
    public static final String LEFT = "G";
    public static final String RIGHT = "D";
    public static final List<String> DIRECTION = List.of(FRONT, LEFT, RIGHT);


    public static EtatLineModel controleLine(final String lineOld) {
        String[] parts = lineOld.replaceAll("\\s+", "").split(HYPHEN);
        String msg = "";
        switch (parts[0]) {
            case LINE_C:
                msg = controleLineC(parts, msg);
                break;
            case LINE_M:
                msg = controleLineM(parts, msg);
                break;
            case LINE_T:
                msg = controleLineT(parts, msg);
                break;
            case LINE_A:
                msg = controleLineA(parts, msg);
                break;
            default:
                return new EtatLineModel(false, "type line not found");
        }

        return new EtatLineModel(msg.isEmpty(), msg);
    }

    private static String controleLineA(String[] parts, String msg) {
        if (parts.length != 6) {
            msg = "Line type C has an incorrect number of fields";
        }else {
            for (int i = 2; i < 3; i++) {
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
            if (!COMPASS_ROSE.contains(parts[4])) {
                if (msg.isEmpty()) {
                    msg = MessageFormat.format("Line type A: field at index 4 is Invalid cardinal point (value: {0})", parts[4]);
                } else {
                    msg = msg.concat(MessageFormat.format(" and field at index 4 is Invalid cardinal point (value: {0})", parts[4]));
                }
            }
            String[] direction = parts[5].split("");
            for (String move : direction) {
                if(!DIRECTION.contains(move)){
                    if (msg.isEmpty()) {
                        msg = MessageFormat.format("Line type A: field at index 5 is Invalid move point (value: {0})", move);
                    } else {
                        msg = msg.concat(MessageFormat.format(" and field at index 5 is Invalid move point (value: {0})", move));
                    }
                }
            }
        }
        return msg;
    }

    private static String controleLineT(String[] parts, String msg) {
        if (parts.length != 3) {
            msg = "Line type T has an incorrect number of fields";
        }else {
            for (int i = 1; i < parts.length; i++) {
                try {
                    Integer.parseInt(parts[i]);
                } catch (NumberFormatException e) {
                    if (msg.isEmpty()) {
                        msg = MessageFormat.format("Line type T: field at index {0} is not a number (value: {1})", i, parts[i]);
                    } else {
                        msg = msg.concat(MessageFormat.format(" and field at index {0} is not a number (value: {1})", i, parts[i]));
                    }
                }
            }
        }
        return msg;
    }

    private static String controleLineM(String[] parts, String msg) {
        if (parts.length != 2) {
            msg = "Line type M has an incorrect number of fields";
        }else {
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
        }
        return msg;
    }

    private static String controleLineC(String[] parts, String msg) {
        if (parts.length != 2) {
            msg = "Line type C has an incorrect number of fields";
        } else {
            for (int i = 1; i < parts.length; i++) {
                try {
                    Integer.parseInt(parts[i]);
                } catch (NumberFormatException e) {
                    if (msg.isEmpty()) {
                        msg = MessageFormat.format("Line type C: field at index {0} is not a number (value: {1})", i, parts[i]);
                    } else {
                        msg = msg.concat(MessageFormat.format(" and field at index {0} is not a number (value: {1})", i, parts[i]));
                    }
                }
            }
        }
        return msg;
    }
}
