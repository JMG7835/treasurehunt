package com.jmg.treasurehunt.domain.port.in.action;


import com.jmg.treasurehunt.domain.model.Hunter;
import com.jmg.treasurehunt.application.tools.tools.TreasureHuntEnum;


public interface ChangeDirection {
    void go(Hunter hunter, TreasureHuntEnum direction);
    boolean direction(String direction);
}
