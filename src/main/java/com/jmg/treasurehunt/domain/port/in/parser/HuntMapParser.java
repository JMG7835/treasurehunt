package com.jmg.treasurehunt.domain.port.in.parser;

import java.util.List;

public interface HuntMapParser {
    int[] parseMapDimensions(List<String> linesC);
}
