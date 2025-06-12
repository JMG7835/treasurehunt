package com.jmg.treasurehunt.infrastructure.parser;

import com.jmg.treasurehunt.domain.port.in.parser.HuntMapParser;
import org.springframework.stereotype.Component;

import java.util.List;

import static com.jmg.treasurehunt.application.utils.FilesUtils.HYPHEN;
import static com.jmg.treasurehunt.application.utils.FilesUtils.REGEX_SPACE;
import static com.jmg.treasurehunt.application.utils.FilesUtils.VOID;

@Component
public class HuntMapParserImpl implements HuntMapParser {

    @Override
    public int[] parseMapDimensions(List<String> linesC) {
        int maxX = 0;
        int maxY = 0;
        for (String line : linesC) {
            String[] parts = line.replaceAll(REGEX_SPACE, VOID).split(HYPHEN);
            int length = Integer.parseInt(parts[1]);
            int height = Integer.parseInt(parts[2]);
            maxX = Math.max(length, maxX);
            maxY = Math.max(height, maxY);
        }
        return new int[]{maxX, maxY};
    }

}
