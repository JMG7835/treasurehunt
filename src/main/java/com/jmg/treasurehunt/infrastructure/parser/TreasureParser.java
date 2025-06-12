package com.jmg.treasurehunt.infrastructure.parser;

import com.jmg.treasurehunt.domain.model.Treasure;
import org.springframework.stereotype.Component;

import java.util.List;

import static com.jmg.treasurehunt.application.utils.FilesUtils.HYPHEN;
import static com.jmg.treasurehunt.application.utils.FilesUtils.REGEX_SPACE;
import static com.jmg.treasurehunt.application.utils.FilesUtils.VOID;

@Component
public class TreasureParser {
    public List<Treasure> parse(List<String> lines) {
        return lines.stream().map(line -> {
            String[] parts = line.replaceAll(REGEX_SPACE, VOID).split(HYPHEN);
            return new Treasure(
                    Integer.parseInt(parts[1]),
                    Integer.parseInt(parts[2]),
                    Integer.parseInt(parts[3])
            );
        }).toList();
    }
}
