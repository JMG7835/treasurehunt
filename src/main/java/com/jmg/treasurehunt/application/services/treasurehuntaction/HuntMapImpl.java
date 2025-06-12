package com.jmg.treasurehunt.application.services.treasurehuntaction;

import com.jmg.treasurehunt.domain.port.in.action.HuntMap;
import org.springframework.stereotype.Component;

import java.util.List;

import static com.jmg.treasurehunt.application.services.utils.FilesUtils.HYPHEN;
import static com.jmg.treasurehunt.application.services.utils.FilesUtils.REGEX_SPACE;
import static com.jmg.treasurehunt.application.services.utils.FilesUtils.VOID;

@Component
public class HuntMapImpl implements HuntMap {

    private static final String MAP = "C";

    @Override
    public String[][] create(List<String> linesC, List<String> result) {
        int x = 0;
        int y = 0;
        for (String line : linesC) {
            String[] parts = line.replaceAll(REGEX_SPACE, VOID).split(HYPHEN);
            int lenght = Integer.parseInt(parts[1]);
            int higth = Integer.parseInt(parts[2]);
            x = Math.max(lenght, x);
            y = Math.max(higth, y);
        }
        result.add(MAP + HYPHEN + x + HYPHEN + y);
        String[][] map = new String[y][x];
        return map;
    }
}
