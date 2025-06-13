package com.jmg.treasurehunt.domain.port.in.action;

import java.util.List;

public interface TreasureGameUseCase {
    public List<String> play(List<String> lines);
}
