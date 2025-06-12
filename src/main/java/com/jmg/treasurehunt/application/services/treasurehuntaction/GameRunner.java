package com.jmg.treasurehunt.application.services.treasurehuntaction;

import com.jmg.treasurehunt.application.tools.tools.TreasureHuntEnum;
import com.jmg.treasurehunt.domain.model.GameState;
import com.jmg.treasurehunt.domain.model.Hunter;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class GameRunner {

    private final MoveFront moveFront;
    private final MoveLeft moveLeft;
    private final MoveRight moveRight;

    public GameRunner(MoveFront moveFront, MoveLeft moveLeft, MoveRight moveRight) {
        this.moveFront = moveFront;
        this.moveLeft = moveLeft;
        this.moveRight = moveRight;
    }

    public void run(GameState state) {
        List<Hunter> hunters = state.getHunters();
        String[][] map = state.getMap();

        int maxMoves = hunters.stream().mapToInt(h -> h.getMoveSet().length).max().orElse(0);

        for (int i = 0; i < maxMoves; i++) {
            for (Hunter hunter : hunters) {
                if (i < hunter.getMoveSet().length) {
                    TreasureHuntEnum direction = TreasureHuntEnum.fromPattern(hunter.getDirection());
                    switch (hunter.getMoveSet()[i]) {
                        case "A" -> moveFront.go(map, hunter, direction);
                        case "G" -> moveLeft.go(hunter, direction);
                        case "D" -> moveRight.go(hunter, direction);
                    }
                }
            }
        }
    }
}
