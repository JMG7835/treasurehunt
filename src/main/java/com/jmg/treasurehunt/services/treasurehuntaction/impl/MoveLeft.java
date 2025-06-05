package com.jmg.treasurehunt.services.treasurehuntaction.impl;

import com.jmg.treasurehunt.model.Hunter;
import com.jmg.treasurehunt.services.treasurehuntaction.ChangeDirection;
import com.jmg.treasurehunt.tools.TreasureHuntEnum;
import org.springframework.stereotype.Component;

import java.text.MessageFormat;

@Component
public class MoveLeft {
    public void go(Hunter hunter, TreasureHuntEnum direction) {
        switch (direction) {
            case NORD -> hunter.setDirection(TreasureHuntEnum.WEST.getPattern());
            case SOUTH -> hunter.setDirection(TreasureHuntEnum.EAST.toString());
            case WEST -> hunter.setDirection(TreasureHuntEnum.SOUTH.getPattern());
            case EAST -> hunter.setDirection(TreasureHuntEnum.NORD.getPattern());
        }
    }
}
