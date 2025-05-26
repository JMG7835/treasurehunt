package com.jmg.treasurehunt.model;


import java.util.List;

/**
 * Model return between processor and writer PlayTreasureHunter batch for check file
 *
 * @Autor: GADEAUD Jean-Michel
 */
public record EtatFileTreasureHuntModel(boolean isOk, String fileName, List<String> lines) {
}

