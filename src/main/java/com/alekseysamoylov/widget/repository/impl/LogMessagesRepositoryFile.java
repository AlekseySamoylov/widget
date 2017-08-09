package com.alekseysamoylov.widget.repository.impl;

import com.alekseysamoylov.widget.model.LogList;
import com.alekseysamoylov.widget.repository.IntervalRepository;
import com.alekseysamoylov.widget.repository.LogMessagesRepository;
import com.alekseysamoylov.widget.utils.CustomLogger;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.alekseysamoylov.widget.constants.DateTimeConstants.DATE_FORMAT_WITH_MS;
import static com.alekseysamoylov.widget.constants.DateTimeConstants.DATE_FORMAT_WITH_MS_REG_EX;

/**
 * Implementation {@link IntervalRepository}
 */
@Repository
public class LogMessagesRepositoryFile implements LogMessagesRepository {

    private static final CustomLogger LOGGER = new CustomLogger();

    @Override
    public NavigableMap<Long, String> findAll(String filePath) {
        return null;
    }

    @Override
    public LogList findLast(Integer interval, String filePath) {
        List<String> list = new ArrayList<>();

        Date currentTime = new Date();
        long currentSeconds = TimeUnit.MILLISECONDS.toSeconds(currentTime.getTime());

        try (Stream<String> stream = Files.lines(Paths.get(filePath))) {
            list = stream
                    .filter(line -> getLogTimePredicate(line, currentSeconds, interval))
                    .collect(Collectors.toList());

        } catch (IOException e) {
            e.printStackTrace();
        }


        return new LogList(currentTime, list);
    }

    private boolean getLogTimePredicate(String string, long currentSeconds, long interval) {

        Pattern pattern = Pattern.compile(DATE_FORMAT_WITH_MS_REG_EX);
        Matcher matcher = pattern.matcher(string);
        while (matcher.find()) {
            String dateLogString = matcher.group();
            try {
                Date logDate = new SimpleDateFormat(DATE_FORMAT_WITH_MS).parse(dateLogString);
                long logDateSeconds = TimeUnit.MILLISECONDS.toSeconds(logDate.getTime());


                if ((currentSeconds - logDateSeconds) <= interval) {
                    return true;
                }
            } catch (ParseException e) {
                LOGGER.error(e.getMessage());
            }
        }
        return false;
    }
}
