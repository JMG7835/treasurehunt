package com.jmg.treasurehunt.application.services.treasurehuntaction;

import com.jmg.treasurehunt.application.tools.TreasureHuntEnum;
import com.jmg.treasurehunt.domain.model.Hunter;
import org.springframework.stereotype.Component;

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
