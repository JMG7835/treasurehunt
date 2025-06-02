package com.jmg.treasurehunt.services;

import com.jmg.treasurehunt.model.EtatFileTreasureHuntModel;

import java.io.File;

public interface FileParser {
    EtatFileTreasureHuntModel parse(File file);
}
