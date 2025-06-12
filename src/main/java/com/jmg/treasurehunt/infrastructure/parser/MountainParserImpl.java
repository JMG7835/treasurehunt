package com.jmg.treasurehunt.infrastructure.parser;

import com.jmg.treasurehunt.domain.port.in.parser.MountainParser;
import org.springframework.stereotype.Component;

import static com.jmg.treasurehunt.application.utils.FilesUtils.HYPHEN;
import static com.jmg.treasurehunt.application.utils.FilesUtils.REGEX_SPACE;
import static com.jmg.treasurehunt.application.utils.FilesUtils.VOID;

@Component
public class MountainParserImpl implements MountainParser {
    public int[] parse(String line) {
        String[] parts = line.replaceAll(REGEX_SPACE, VOID).split(HYPHEN);
        int x = Integer.parseInt(parts[1]);
        int y = Integer.parseInt(parts[2]);
        return new int[]{x, y};
    }
}
