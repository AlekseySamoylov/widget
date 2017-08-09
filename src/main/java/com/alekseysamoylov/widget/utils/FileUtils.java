package com.alekseysamoylov.widget.utils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;


public class FileUtils {

    public static void createLogFile(Path path) throws IOException {
            if (!Files.exists(path)){
                Path parentDirectories = path.getParent();
                if (parentDirectories != null) {
                    Files.createDirectories(parentDirectories);
                }
                Files.createFile(path);
            }
        }
}
