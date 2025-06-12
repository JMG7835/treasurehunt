package com.jmg.treasurehunt.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class GameState {
    private String[][] map;
    private List<Hunter> hunters;
    private List<String> result;


}
