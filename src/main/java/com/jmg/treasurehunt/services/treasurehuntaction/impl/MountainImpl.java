package com.jmg.treasurehunt.services.treasurehuntaction.impl;

import com.jmg.treasurehunt.services.treasurehuntaction.Mountain;
import org.springframework.stereotype.Component;

import java.util.List;

import static com.jmg.treasurehunt.utils.FilesUtils.*;
import static com.jmg.treasurehunt.utils.FilesUtils.HYPHEN;

@Component
public class MountainImpl implements Mountain {

    public static final String MONT = "M";

    @Override
    public String[][] addMountain(final String[][] map, final List<String> lineM, List<String> result) {
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
}
