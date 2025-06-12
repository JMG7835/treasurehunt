package com.jmg.treasurehunt.domain.port.in.action;

import java.util.List;

public interface Mountain {
    String[][] addMountain(final String[][] map, final List<String> lineM, List<String> result);
}
