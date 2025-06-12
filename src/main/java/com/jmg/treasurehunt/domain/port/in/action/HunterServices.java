package com.jmg.treasurehunt.domain.port.in.action;

import com.jmg.treasurehunt.domain.model.Hunter;

import java.util.List;

public interface HunterServices {
    List<Hunter> createHunter(final String[][] map, final List<String> lineA);
}