package com.jmg.treasurehunt.services.treasurehuntaction;


import com.jmg.treasurehunt.model.Hunter;
import com.jmg.treasurehunt.tools.TreasureHuntEnum;


public interface ChangeDirection {
    void go(Hunter hunter, TreasureHuntEnum direction);
    boolean direction(String direction);
}
