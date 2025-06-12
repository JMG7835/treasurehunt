package com.jmg.treasurehunt.application.services.treasurehuntaction;

import com.jmg.treasurehunt.domain.model.Treasure;
import com.jmg.treasurehunt.domain.port.in.action.TreasureService;
import com.jmg.treasurehunt.infrastructure.parser.TreasureParser;
import org.springframework.stereotype.Component;

import java.util.List;

import static com.jmg.treasurehunt.application.utils.FilesUtils.TREASURE;

@Component
public class TreasureServiceImpl implements TreasureService {
    private final TreasureParser treasureParser;

    public TreasureServiceImpl(TreasureParser treasureParser) {
        this.treasureParser = treasureParser;
    }


    @Override
    public void addTreasure(final String[][] map, final List<String> lineT) {
        final int maxX = map[0].length;
        final int maxY = map.length;
        List<Treasure> treasures = treasureParser.parse(lineT);
        for (Treasure treasure : treasures) {
            int x = treasure.getX();
            int y = treasure.getY();
            if (maxX >= x && maxY >= y) {
                String current = map[y][x];
                int value = 0;

                if (current != null && current.startsWith(TREASURE)) {
                    value = Integer.parseInt(current.substring(2, 3));
                }

                map[y][x] = TREASURE + "(" + (treasure.getCount() + value) + ")";
            }
        }
    }
}
