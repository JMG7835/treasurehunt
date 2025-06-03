package com.jmg.treasurehunt.services;

import com.jmg.treasurehunt.model.Hunter;
import com.jmg.treasurehunt.tools.TreasureHuntEnum;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import static com.jmg.treasurehunt.utils.FilesUtils.*;

/**
 * Service for PlayTreasureHunter
 * He controle line of file and play  the game
 *
 * @Autor: GADEAUD Jean-Michel
 */

@Service
public class TreasureHuntFileServices {



    //Generate and move all treasure hunters and add treasure funded
    private void playHunters(final String[][] map, final List<String> lineA, List<String> result) {

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
                if (position != null && position.startsWith(TREASURE)) {
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
            } else {
                map[hunter.getY()][hunter.getX()] = oldMapCase.substring(1);
            }

            if (mapCase != null && mapCase.startsWith(TREASURE)) {
                treasureManager(hunter, mapCase, x, map[y]);
            } else {
                map[y][x] = HUNTER;
            }
            hunter.setX(x);
            hunter.setY(y);
        }
    }

    private static void treasureManager(Hunter hunter, String mapCase, int x, String[] map) {
        int value = Integer.parseInt(mapCase.substring(2, 3));
        if (value > 0) {
            value--;
            hunter.setTreasure(hunter.getTreasure() + 1);
        }
        map[x] = HUNTER + (value > 0 ? TREASURE + "(" + value + ")" : "");
    }

}
