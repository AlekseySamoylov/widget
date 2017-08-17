package com.alekseysamoylov.widget.service;

import com.alekseysamoylov.widget.exception.LogWidgetException;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.annotation.PreDestroy;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.StandardWatchEventKinds;
import java.nio.file.WatchService;

/**
 * The file changes scanner in the separate thread.
 */
@Service
public class FolderScannerAsyncService {

    private volatile Boolean running = false;

    @Async
    void startScan(FileChangeScanner fileChangeScanner, Path logWidgetPath) throws InterruptedException, LogWidgetException, ParserConfigurationException, IOException {
        running = true;
        try (final WatchService watchService = FileSystems.getDefault().newWatchService()) {
            Path parentDirectories = logWidgetPath.getParent();
            if (parentDirectories != null) {
                logWidgetPath.getParent().register(watchService, StandardWatchEventKinds.ENTRY_MODIFY);
            }
            while (isRunning()) {
                fileChangeScanner.watchFolderIteration(watchService, logWidgetPath);
            }
            Thread.currentThread().interrupt();
        }
    }

    @PreDestroy
    public void stopScanner() {
        running = false;
    }


    boolean isRunning() {
        return running;
    }
}
