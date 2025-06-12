package com.jmg.treasurehunt.application.services.treasurehuntaction;

import com.jmg.treasurehunt.application.tools.tools.TreasureHuntEnum;
import com.jmg.treasurehunt.domain.model.Hunter;
import com.jmg.treasurehunt.domain.port.in.action.HuntMap;
import com.jmg.treasurehunt.domain.port.in.action.HunterServices;
import com.jmg.treasurehunt.domain.port.in.action.Mountain;
import com.jmg.treasurehunt.domain.port.in.action.Treasure;
import com.jmg.treasurehunt.domain.port.in.action.TreasureGame;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

import static com.jmg.treasurehunt.application.services.utils.FilesUtils.HYPHEN;
import static com.jmg.treasurehunt.application.services.utils.FilesUtils.TREASURE;

@Component
public class TreasureGameImpl implements TreasureGame {

    @Autowired
    HuntMap huntMap;
    @Autowired
    Mountain mountain;
    @Autowired
    Treasure treasure;
    @Autowired
    HunterServices hunterServices;
    @Autowired
    MoveFront moveFront;
    @Autowired
    MoveLeft moveLeft;
    @Autowired
    MoveRight moveRight;

    @Override
    public List<String> play(List<String> lines) {
        lines = lines.stream()
                .map(String::trim)
                .toList();
        List<String> result = new ArrayList<>();
        String[][] map = huntMap.create(lines.stream()
                .filter(line -> line.startsWith(TreasureHuntEnum.LINE_C.getPattern())).toList(), result);
        mountain.addMountain(map, lines.stream()
                .filter(line -> line.startsWith(TreasureHuntEnum.LINE_M.getPattern())).toList(), result);
        treasure.addTreasure(map, lines.stream()
                .filter(line -> line.startsWith(TreasureHuntEnum.LINE_T.getPattern())).toList());
        List<Hunter> hunters = hunterServices.createHunter(map, lines.stream()
                .filter(line -> line.startsWith(TreasureHuntEnum.LINE_A.getPattern())).toList());
        int maxMove = hunters.stream().mapToInt(h -> h.getMoveSet().length)
                .max()
                .orElse(0);
        for (int i = 0; i < maxMove; i++) {
            for (Hunter hunter : hunters) {
                if (i < hunter.getMoveSet().length) {
                    TreasureHuntEnum direction = TreasureHuntEnum.fromPattern(hunter.getDirection());
                    switch (hunter.getMoveSet()[i]) {
                        case "A" -> moveFront.go(map, hunter, direction);
                        case "G" -> moveLeft.go(hunter, direction);
                        case "D" -> moveRight.go(hunter, direction);
                    }
                }
            }
        }
        for (int x = 0; x < map[0].length; x++) {
            for (int y = 0; y < map.length; y++) {
                String position = map[y][x];
                if (position != null && position.startsWith(TREASURE)) {
                    result.add(TREASURE + HYPHEN + x + HYPHEN + y + HYPHEN + position.charAt(2));
                }
            }
        }
        for (Hunter hunter : hunters) {
            result.add("A" + HYPHEN + hunter.getName() + HYPHEN + hunter.getX() + HYPHEN + hunter.getY() + HYPHEN + hunter.getDirection() + HYPHEN + hunter.getTreasure());
        }

        return result;
    }
}
