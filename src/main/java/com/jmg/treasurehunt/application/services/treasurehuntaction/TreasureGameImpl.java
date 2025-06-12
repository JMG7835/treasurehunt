package com.jmg.treasurehunt.application.services.treasurehuntaction;

import com.jmg.treasurehunt.domain.model.GameState;
import com.jmg.treasurehunt.domain.port.in.action.TreasureGame;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class TreasureGameImpl implements TreasureGame {

    private final GameInitializer initializer;
    private final GameRunner runner;
    private final GameResultFormatter formatter;

    public TreasureGameImpl(GameInitializer initializer, GameRunner runner, GameResultFormatter formatter) {
        this.initializer = initializer;
        this.runner = runner;
        this.formatter = formatter;
    }


    @Override
    public List<String> play(List<String> lines) {
        GameState game = initializer.initialize(lines);
        runner.run(game);
        return formatter.format(game);
    }
}
