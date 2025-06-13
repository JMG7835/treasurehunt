package com.jmg.treasurehunt.application.services.treasurehuntaction;

import com.jmg.treasurehunt.domain.port.in.action.HuntMapUseCase;
import com.jmg.treasurehunt.domain.port.in.parser.HuntMapParser;
import org.springframework.stereotype.Component;

import java.util.List;

import static com.jmg.treasurehunt.application.utils.FilesUtils.HYPHEN;
import static com.jmg.treasurehunt.application.utils.FilesUtils.MAP;

@Component
public class HuntMapService implements HuntMapUseCase {

    private final HuntMapParser huntMapParser;

    public HuntMapService(HuntMapParser huntMapParser) {
        this.huntMapParser = huntMapParser;
    }

    @Override
    public String[][] create(List<String> linesC, List<String> result) {
        int[] dimensions = huntMapParser.parseMapDimensions(linesC);
        int x = dimensions[0];
        int y = dimensions[1];
        result.add(MAP + HYPHEN + x + HYPHEN + y);
        String[][] map = new String[y][x];

        // Map Initialisation with default value
        for (int i = 0; i < y; i++) {
            for (int j = 0; j < x; j++) {
                map[i][j] = "";
            }
        }

        return map;
    }
}

