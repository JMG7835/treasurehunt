package com.jmg.treasurehunt.services.treasurehuntaction.impl;

import com.jmg.treasurehunt.services.treasurehuntaction.Treasure;
import org.springframework.stereotype.Component;

import java.util.List;

import static com.jmg.treasurehunt.utils.FilesUtils.*;

@Component
public class TreasureImpl implements Treasure {


    @Override
    public void addTreasure(final String[][] map, final List<String> lineT) {
        final int maxX = map[0].length;
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
}
