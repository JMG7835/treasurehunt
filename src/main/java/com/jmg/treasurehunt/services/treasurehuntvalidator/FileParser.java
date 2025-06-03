package com.jmg.treasurehunt.services.treasurehuntvalidator;

import com.jmg.treasurehunt.model.EtatFileTreasureHuntModel;

import java.util.List;

public interface FileParser {
    EtatFileTreasureHuntModel parse(List<String> file, String fileName);
}
