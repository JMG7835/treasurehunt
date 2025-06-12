package com.jmg.treasurehunt.adapter.batch.listener;

import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Slf4j
@Component
public class ArchiveListener implements StepExecutionListener {

    @Value("${treasure_file.path.archive}")
    private String archiveDir;
    @Setter
    private File lastFile;


    @Override
    public ExitStatus afterStep(StepExecution stepExecution) {
        if (lastFile != null) {
            try {
                Path archivePath = Paths.get(archiveDir, lastFile.getName());
                Files.createDirectories(Paths.get(archiveDir));
                Files.move(lastFile.toPath(), archivePath, StandardCopyOption.REPLACE_EXISTING);
                System.out.println("Fichier archivé : " + lastFile.getName());
            } catch (IOException e) {
                throw new RuntimeException("Erreur lors de l'archivage", e);
            }
        }
        return ExitStatus.COMPLETED;
    }
}
