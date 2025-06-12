package com.jmg.treasurehunt.domain.port.in.validator;

import com.jmg.treasurehunt.domain.model.EtatFileTreasureHuntModel;

import java.util.List;

public interface FileParser {
    EtatFileTreasureHuntModel parse(List<String> file, String fileName);
}
