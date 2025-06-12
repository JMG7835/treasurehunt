package com.jmg.treasurehunt.application.services.treasurehuntaction;

import com.jmg.treasurehunt.domain.port.in.action.Treasure;
import org.springframework.stereotype.Component;

import java.util.List;

import static com.jmg.treasurehunt.application.services.utils.FilesUtils.HYPHEN;
import static com.jmg.treasurehunt.application.services.utils.FilesUtils.REGEX_SPACE;
import static com.jmg.treasurehunt.application.services.utils.FilesUtils.TREASURE;
import static com.jmg.treasurehunt.application.services.utils.FilesUtils.VOID;

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
