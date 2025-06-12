package com.jmg.treasurehunt.application.services.treasurehuntaction;

import com.jmg.treasurehunt.application.tools.tools.TreasureHuntEnum;
import com.jmg.treasurehunt.domain.model.Hunter;
import org.springframework.stereotype.Component;

import static com.jmg.treasurehunt.application.services.utils.FilesUtils.HUNTER;
import static com.jmg.treasurehunt.application.services.utils.FilesUtils.TREASURE;

@Component
public class MoveFront {
    public void go(String[][] map, Hunter hunter, TreasureHuntEnum direction) {
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

    private void treasureManager(Hunter hunter, String mapCase, int x, String[] map) {
        int value = Integer.parseInt(mapCase.substring(2, 3));
        if (value > 0) {
            value--;
            hunter.setTreasure(hunter.getTreasure() + 1);
        }
        map[x] = HUNTER + (value > 0 ? TREASURE + "(" + value + ")" : "");
    }

}
