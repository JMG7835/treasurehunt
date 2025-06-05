package com.jmg.treasurehunt.services.treasurehuntaction;

import com.jmg.treasurehunt.model.Hunter;

import java.util.List;

public interface HunterServices {
    List<Hunter> createHunter(final String[][] map, final List<String> lineA);
}