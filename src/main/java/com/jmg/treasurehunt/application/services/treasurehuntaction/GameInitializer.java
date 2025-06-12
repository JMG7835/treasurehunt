package com.jmg.treasurehunt.application.services.treasurehuntaction;

import com.jmg.treasurehunt.domain.model.GameState;
import com.jmg.treasurehunt.domain.model.Hunter;
import com.jmg.treasurehunt.domain.port.in.action.HuntMap;
import com.jmg.treasurehunt.domain.port.in.action.HunterServices;
import com.jmg.treasurehunt.domain.port.in.action.Mountain;
import com.jmg.treasurehunt.domain.port.in.action.TreasureService;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class GameInitializer {
    private final HuntMap huntMap;
    private final Mountain mountain;
    private final TreasureService treasure;
    private final HunterServices hunterServices;

    public GameInitializer(HuntMap huntMap, Mountain mountain, TreasureService treasure, HunterServices hunterServices) {
        this.huntMap = huntMap;
        this.mountain = mountain;
        this.treasure = treasure;
        this.hunterServices = hunterServices;
    }

    public GameState initialize(List<String> lines) {
        List<String> result = new ArrayList<>();
        lines = lines.stream().map(String::trim).toList();
        String[][] map = huntMap.create(
                lines.stream().filter(l -> l.startsWith("C")).toList(), result);
        mountain.addMountain(map, lines.stream().filter(l -> l.startsWith("M")).toList(), result);
        treasure.addTreasure(map, lines.stream().filter(l -> l.startsWith("T")).toList());
        List<Hunter> hunters = hunterServices.createHunter(map, lines.stream().filter(l -> l.startsWith("A")).toList());

        return new GameState(map, hunters, result);
    }
}
