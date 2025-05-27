package com.jmg.treasurehunt.services;

import com.jmg.treasurehunt.model.EtatLineModel;
import com.jmg.treasurehunt.model.Hunter;
import com.jmg.treasurehunt.tools.TreasureHuntEnum;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Stream;

import static com.jmg.treasurehunt.utils.MyFilesUtils.*;

/**
 * Service for PlayTreasureHunter
 * He controle line of file and play  the game
 *
 * @Autor: GADEAUD Jean-Michel
 */

@Service
public class TreasureHuntFileServices {


    public static final String MONT = "M";
    public static final String TREASURE = "T";
    public static final String CARD = "C";
    public static final String HUNTER = "H";

    public EtatLineModel controleLine(final String lineOld) {
        String[] parts = lineOld.replaceAll(REGEX_SPACE, VOID).split(HYPHEN);
        String msg = "";
        TreasureHuntEnum typeLine = TreasureHuntEnum.fromPattern(parts[0]);
        switch (typeLine) {
            case LINE_C -> msg = controleLineC(parts, msg);
            case LINE_M -> msg = controleLineM(parts, msg);
            case LINE_T -> msg = controleLineT(parts, msg);
            case LINE_A -> msg = controleLineA(parts, msg);
            default -> {
                return new EtatLineModel(false, MessageFormat.format("Type line {0} not found", parts[0]));
            }
        }

        return new EtatLineModel(msg.isEmpty(), msg);
    }

    private static String controleLineA(String[] parts, String msg) {
        if (parts.length != 6) {
            msg = "Line type A has an incorrect number of fields";
        } else {
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
        }
        return msg;
    }

    private static String controleLineT(String[] parts, String msg) {
        if (parts.length != 4) {
            msg = "Line type T has an incorrect number of fields";
        } else {
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
        if (parts.length != 3) {
            msg = "Line type M has an incorrect number of fields";
        } else {
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
        if (parts.length != 3) {
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

    public List<String> play(List<String> lines) {
        List<String> result = new ArrayList<>();
        String[][] map = createMap(lines.stream().filter(line -> line.startsWith(TreasureHuntEnum.LINE_C.getPattern())).toList(), result);
        addMountain(map, lines.stream().filter(line -> line.startsWith(TreasureHuntEnum.LINE_M.getPattern())).toList(), result);
        addTreasure(map, lines.stream().filter(line -> line.startsWith(TreasureHuntEnum.LINE_T.getPattern())).toList());
        playHunters(map, lines.stream().filter(line -> line.startsWith(TreasureHuntEnum.LINE_A.getPattern())).toList(), result);

        return result;
    }

    //Generate the biggest map in line if multiple line C
    private static String[][] createMap(List<String> linesC, List<String> result) {
        int x = 0;
        int y = 0;
        for (String line : linesC) {
            String[] parts = line.replaceAll(REGEX_SPACE, VOID).split(HYPHEN);
            int lenght = Integer.parseInt(parts[1]);
            int higth = Integer.parseInt(parts[2]);
            x = Math.max(lenght, x);
            y = Math.max(higth, y);
        }
        result.add(CARD + HYPHEN + x + HYPHEN + y);
        String[][] map = new String[y][x];
        return map;
    }

    //Generate the mountain in map if is in map
    private String[][] addMountain(final String[][] map, final List<String> lineM, List<String> result) {
        final int maxX = map[0].length;
        final int maxY = map.length;
        for (String line : lineM) {
            String[] parts = line.replaceAll(REGEX_SPACE, VOID).split(HYPHEN);
            int x = Integer.parseInt(parts[1]);
            int y = Integer.parseInt(parts[2]);

            if (maxX >= x && maxY >= y) {
                map[y][x] = MONT;
                result.add(MONT + HYPHEN + x + HYPHEN + y);
            }
        }
        return map;
    }

    //Generate the treasure and increment if is already set in map if is in map
    private void addTreasure(final String[][] map, final List<String> lineT) {
        final int maxX =  map[0].length;
        final int maxY = map.length;
        for (String line : lineT) {
            String[] parts = line.replaceAll(REGEX_SPACE, VOID).split(HYPHEN);
            int x = Integer.parseInt(parts[1]);
            int y = Integer.parseInt(parts[2]);
            int t = Integer.parseInt(parts[3]);

            if (maxX >= x && maxY >= y) {
                String previouse = map[y][x];
                int value = 0;
                if (previouse != null && previouse.startsWith(TREASURE)) {
                    value = Integer.parseInt(previouse.substring(2, 3));
                }
                map[y][x] = TREASURE + "(" + (t + value) + ")";
            }
        }
    }

    //Generate and move all treasure hunters and add treasure funded
    private void playHunters(final String[][] map, final List<String> lineA, List<String> result) {
        final int maxX = map[0].length;
        final int maxY = map.length;
        int maxMove = 0;
        List<Hunter> hunters = new ArrayList<>();
        for (String line : lineA) {
            String[] parts = line.replaceAll(REGEX_SPACE, VOID).split(HYPHEN);
            int x = Integer.parseInt(parts[2]);
            int y = Integer.parseInt(parts[3]);
            String[] move = parts[5].split(VOID);
            maxMove = Math.max(maxMove, move.length);
            if (maxX >= x && maxY >= y) {
                map[y][x] = HUNTER;
                hunters.add(new Hunter(parts[1], 0, parts[4], move, x, y));
            }
        }
        hunters.sort(Comparator.comparing(Hunter::getName));
        for (int i = 0; i < maxMove; i++) {
            for (Hunter hunter : hunters) {
                if (i < hunter.getMoveSet().length) {
                    TreasureHuntEnum direction = TreasureHuntEnum.fromPattern(hunter.getDirection());
                    switch (hunter.getMoveSet()[i]) {
                        case "A" -> frontMove(map, hunter, direction);
                        case "G" -> leftMove(hunter, direction);
                        case "D" -> rightMove(hunter, direction);
                    }
                }
            }
        }
        for (int x = 0; x < maxX; x++) {
            for (int y = 0; y < maxY; y++) {
                String position = map[y][x];
                if (position!= null && position.startsWith(TREASURE)) {
                    result.add(TREASURE + HYPHEN + x + HYPHEN + y + HYPHEN + position.charAt(2));
                }
            }
        }
        for (Hunter hunter : hunters) {
            result.add("A" + HYPHEN + hunter.getName() + HYPHEN + hunter.getX() + HYPHEN + hunter.getY() + HYPHEN + hunter.getDirection() + HYPHEN + hunter.getTreasure());
        }
    }

    private static void rightMove(Hunter hunter, TreasureHuntEnum direction) {
        switch (direction) {
            case NORD -> hunter.setDirection(TreasureHuntEnum.EAST.getPattern());
            case SOUTH -> hunter.setDirection(TreasureHuntEnum.WEST.toString());
            case WEST -> hunter.setDirection(TreasureHuntEnum.NORD.getPattern());
            case EAST -> hunter.setDirection(TreasureHuntEnum.SOUTH.getPattern());
        }
    }

    private static void leftMove(Hunter hunter, TreasureHuntEnum direction) {
        switch (direction) {
            case NORD -> hunter.setDirection(TreasureHuntEnum.WEST.getPattern());
            case SOUTH -> hunter.setDirection(TreasureHuntEnum.EAST.toString());
            case WEST -> hunter.setDirection(TreasureHuntEnum.SOUTH.getPattern());
            case EAST -> hunter.setDirection(TreasureHuntEnum.NORD.getPattern());
        }
    }

    private static void frontMove(String[][] map, Hunter hunter, TreasureHuntEnum direction) {
        int oldX;
        int oldY;
        int x = oldX = hunter.getX();
        int y = oldY = hunter.getY();
        switch (direction) {
            case NORD:
                y--;
                break;
            case SOUTH:
                y++;
                break;
            case EAST:
                x++;
                break;
            case WEST:
                x--;
        }
        String mapCase = map[y][x];
        if (mapCase == null || mapCase.isEmpty() || mapCase.startsWith(TREASURE)) {
            String oldMapCase = map[oldY][oldX];
            if (oldMapCase == null || oldMapCase.isEmpty()) {
                map[hunter.getY()][hunter.getX()] = "";
            }else{
                map[hunter.getY()][hunter.getX()] = oldMapCase.substring(1);
            }

            if (mapCase!=null && mapCase.startsWith(TREASURE)) {
                treasurGestion(hunter, mapCase, x, map[y]);
            } else {
                map[y][x] = HUNTER;
            }
            hunter.setX(x);
            hunter.setY(y);
        }
    }

    private static void treasurGestion(Hunter hunter, String mapCase, int x, String[] map) {
        int value = Integer.parseInt(mapCase.substring(2, 3));
        if (value > 0) {
            value--;
            hunter.setTreasure(hunter.getTreasure() + 1);
        }
        map[x] = HUNTER + (value> 0 ?  TREASURE + "(" + value + ")" : "");
    }

}
