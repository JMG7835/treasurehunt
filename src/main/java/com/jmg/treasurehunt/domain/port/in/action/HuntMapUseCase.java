package com.jmg.treasurehunt.domain.port.in.action;

import java.util.List;

public interface HuntMapUseCase {
    String[][] create(List<String> linesC, List<String> result);
}
