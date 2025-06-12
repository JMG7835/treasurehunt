package com.jmg.treasurehunt.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class Treasure {
    private int x;
    private int y;
    private int count;
}
