package com.jmg.treasurehunt.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * Model for hunter gestion
 *
 * @Autor: GADEAUD Jean-Michel
 */
@Getter
@Setter
@AllArgsConstructor
public class Hunter {
    private String name;
    private int treasure;
    private String direction;
    private String[] moveSet;
    private int x;
    private int y;
}
