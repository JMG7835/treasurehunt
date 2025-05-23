package com.jmg.treasurehunt.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.io.File;
import java.util.List;

/**
 * Model return between processor and writer PlayTreasureHunter batch for check file
 *
 * @Autor: GADEAUD Jean-Michel
 */
@Getter
@AllArgsConstructor
public class EtatFileTreasureHunt {
    private final boolean isOk;
    private final String fileName;
    private final List<String> lines;
}

