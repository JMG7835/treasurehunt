package com.jmg.treasurehunt.services.treasurehuntaction.impl;

import com.jmg.treasurehunt.model.Hunter;
import com.jmg.treasurehunt.services.treasurehuntaction.HunterServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import static com.jmg.treasurehunt.utils.FilesUtils.*;
import static com.jmg.treasurehunt.utils.FilesUtils.VOID;

@Component
public class HunterServicesImpl implements HunterServices {

    @Override
    public List<Hunter> createHunter(final String[][] map, final List<String> lineA){
        final int maxX = map[0].length;
        final int maxY = map.length;
        List<Hunter> hunters = new ArrayList<>();
        for (String line : lineA) {
            String[] parts = line.replaceAll(REGEX_SPACE, VOID).split(HYPHEN);
            int x = Integer.parseInt(parts[2]);
            int y = Integer.parseInt(parts[3]);
            String[] move = parts[5].split(VOID);
            if (maxX >= x && maxY >= y) {
                map[y][x] = HUNTER;
                hunters.add(new Hunter(parts[1], 0, parts[4], move, x, y));
            }
        }
        hunters.sort(Comparator.comparing(Hunter::getName));
        return hunters;
    }


}
