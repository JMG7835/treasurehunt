package com.jmg.treasurehunt.application.services.treasurehuntvalidator;

import com.jmg.treasurehunt.domain.port.in.validator.FileArchiver;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Component
public class FileArchiverImpl implements FileArchiver {

    @Value("${treasure_file.path.archive}")
    private String archiveDir;

    @Override
    public void archive(File file) {
        try {
            Path archivePath = Paths.get(archiveDir, file.getName());
            Files.createDirectories(Paths.get(archiveDir));
            Files.move(file.toPath(), archivePath, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            throw new RuntimeException("Failed to archive file: " + file.getName(), e);
        }
    }
}
