package com.jmg.treasurehunt.services;

import com.jmg.treasurehunt.model.EtatLineModel;
import com.jmg.treasurehunt.tools.TreasureHuntEnum;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import static com.jmg.treasurehunt.tools.TreasureHuntEnum.LINE_C;
import static com.jmg.treasurehunt.tools.TreasureHuntEnum.LINE_M;
import static com.jmg.treasurehunt.tools.TreasureHuntEnum.LINE_T;
import static com.jmg.treasurehunt.tools.TreasureHuntEnum.LINE_A;
import static com.jmg.treasurehunt.utils.MyFilesUtils.HYPHEN;

@Service
public class TreasureHuntFileServices {


    public static EtatLineModel controleLine(final String lineOld) {
        String[] parts = lineOld.replaceAll("\\s+", "").split(HYPHEN);
        String msg = "";
            TreasureHuntEnum typeLine = TreasureHuntEnum.fromPattern(parts[0]);
            switch (typeLine) {
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
                    return new EtatLineModel(false, MessageFormat.format("Type line {0} not found",parts[0]));
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
            if (!TreasureHuntEnum.isCompassRose(parts[4])) {
                if (msg.isEmpty()) {
                    msg = MessageFormat.format("Line type A: field at index 4 is Invalid cardinal point (value: {0})", parts[4]);
                } else {
                    msg = msg.concat(MessageFormat.format(" and field at index 4 is Invalid cardinal point (value: {0})", parts[4]));
                }
            }
            String[] direction = parts[5].split("");
            for (String move : direction) {
                if(!TreasureHuntEnum.isMoveAutorised(move)){
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

    public List<String> play(Stream<String> lines) {
        List<String> returnLine = new ArrayList<>();
        return returnLine;
    }
}
