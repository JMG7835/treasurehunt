package com.jmg.treasurehunt.application.services.treasurehuntaction;

import com.jmg.treasurehunt.application.tools.tools.TreasureHuntEnum;
import com.jmg.treasurehunt.domain.model.Hunter;
import org.springframework.stereotype.Component;

@Component
public class MoveRight {

    public void go(Hunter hunter, TreasureHuntEnum direction) {
        switch (direction) {
            case NORD -> hunter.setDirection(TreasureHuntEnum.EAST.getPattern());
            case SOUTH -> hunter.setDirection(TreasureHuntEnum.WEST.toString());
            case WEST -> hunter.setDirection(TreasureHuntEnum.NORD.getPattern());
            case EAST -> hunter.setDirection(TreasureHuntEnum.SOUTH.getPattern());
        }
    }

}
