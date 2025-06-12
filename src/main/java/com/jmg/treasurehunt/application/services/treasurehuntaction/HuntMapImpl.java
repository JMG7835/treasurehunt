package com.jmg.treasurehunt.application.services.treasurehuntaction;

import com.jmg.treasurehunt.domain.port.in.action.HuntMap;
import com.jmg.treasurehunt.domain.port.in.parser.HuntMapParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

import static com.jmg.treasurehunt.application.utils.FilesUtils.HYPHEN;
import static com.jmg.treasurehunt.application.utils.FilesUtils.MAP;
import static com.jmg.treasurehunt.application.utils.FilesUtils.REGEX_SPACE;
import static com.jmg.treasurehunt.application.utils.FilesUtils.VOID;

@Component
public class HuntMapImpl implements HuntMap {

    private final HuntMapParser huntMapParser;

    public HuntMapImpl(HuntMapParser huntMapParser) {
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

