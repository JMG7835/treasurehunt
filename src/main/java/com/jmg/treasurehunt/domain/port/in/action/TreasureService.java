package com.jmg.treasurehunt.domain.port.in.action;

import java.util.List;

public interface TreasureService {
    void addTreasure(final String[][] map, final List<String> lineT);

}
