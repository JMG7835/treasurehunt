package com.jmg.treasurehunt.application.services.treasurehuntaction;

import com.jmg.treasurehunt.domain.model.GameState;
import com.jmg.treasurehunt.domain.model.Hunter;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class GameResultFormatter {
    public List<String> format(GameState state) {
        List<String> result = new ArrayList<>(state.getResult());
        String[][] map = state.getMap();

        for (int x = 0; x < map[0].length; x++) {
            for (int y = 0; y < map.length; y++) {
                String cell = map[y][x];
                if (cell != null && cell.startsWith("T")) {
                    result.add("T-" + x + "-" + y + "-" + cell.charAt(2));
                }
            }
        }

        for (Hunter hunter : state.getHunters()) {
            result.add("A-" + hunter.getName() + "-" + hunter.getX() + "-" + hunter.getY() + "-" + hunter.getDirection() + "-" + hunter.getTreasure());
        }

        return result;
    }
}
