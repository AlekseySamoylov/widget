package com.alekseysamoylov.widget.utils;

import com.alekseysamoylov.widget.exception.LogWidgetCriticalException;
import com.alekseysamoylov.widget.service.FileChangeScanner;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.text.SimpleDateFormat;
import java.util.Date;

import static com.alekseysamoylov.widget.constants.CustomLoggerConstants.*;
import static com.alekseysamoylov.widget.constants.DateTimeConstants.DATE_FORMAT_WITH_MS;

/**
 * Custom logger;
 */
public class CustomLogger {

    public void info(String message) {
        String time = new SimpleDateFormat(DATE_FORMAT_WITH_MS)
                .format(new Date());
        String logRecord = String.format(LOG_MESSAGE, time, INFO, message);
        writeToFile(logRecord);
    }

    public void warn(String message) {
        String time = new SimpleDateFormat(DATE_FORMAT_WITH_MS)
                .format(new Date());
        String logRecord = String.format(LOG_MESSAGE, time, WARNING, message);
        writeToFile(logRecord);
    }

    public void error(String message) {
        String time = new SimpleDateFormat(DATE_FORMAT_WITH_MS)
                .format(new Date());
        String logRecord = String.format(LOG_MESSAGE, time, ERROR, message);
        writeToFile(logRecord);
    }

    private void writeToFile(String logRecord) {
        try {
            Path path = Paths.get(FileChangeScanner.getFilePathForLogger());
            FileUtils.createLogFile(path);
            Files.write(path, logRecord.getBytes(), StandardOpenOption.APPEND);
        } catch (IOException e) {
            throw new LogWidgetCriticalException("Logger can't write the file", e);
        }
    }


}
