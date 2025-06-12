package com.jmg.treasurehunt.application.services.treasurehuntaction;

import com.jmg.treasurehunt.domain.port.in.action.Mountain;
import com.jmg.treasurehunt.domain.port.in.parser.MountainParser;
import org.springframework.stereotype.Component;

import java.util.List;

import static com.jmg.treasurehunt.application.utils.FilesUtils.HYPHEN;
import static com.jmg.treasurehunt.application.utils.FilesUtils.MONT;

@Component
public class MountainImpl implements Mountain {

    private final MountainParser mountainParser;

    public MountainImpl(MountainParser mountainParser) {
        this.mountainParser = mountainParser;
    }

    @Override
    public String[][] addMountain(final String[][] map, final List<String> lineM, List<String> result) {
        final int maxX = map[0].length;
        final int maxY = map.length;

        for (String line : lineM) {
            int[] coords = mountainParser.parse(line);
            int x = coords[0];
            int y = coords[1];

            if (maxX >= x && maxY >= y) {
                map[y][x] = MONT;
                result.add(MONT + HYPHEN + x + HYPHEN + y);
            }
        }
        return map;
    }
}
