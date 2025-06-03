package com.jmg.treasurehunt.services.treasurehuntaction.impl;

import com.jmg.treasurehunt.services.treasurehuntaction.TreasureGame;
import com.jmg.treasurehunt.tools.TreasureHuntEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class TreasureGameImpl implements TreasureGame {

    @Autowired
    public List<String> play(List<String> lines){
        lines = lines.stream()
                .map(String::trim)
                .toList();
        List<String> result = new ArrayList<>();
        String[][] map = createMap(lines.stream().filter(line -> line.startsWith(TreasureHuntEnum.LINE_C.getPattern())).toList(), result);
        addMountain(map, lines.stream().filter(line -> line.startsWith(TreasureHuntEnum.LINE_M.getPattern())).toList(), result);
        addTreasure(map, lines.stream().filter(line -> line.startsWith(TreasureHuntEnum.LINE_T.getPattern())).toList());
        playHunters(map, lines.stream().filter(line -> line.startsWith(TreasureHuntEnum.LINE_A.getPattern())).toList(), result);

        return result;
    }
}
