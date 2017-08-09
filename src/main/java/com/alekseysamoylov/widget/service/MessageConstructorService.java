package com.alekseysamoylov.widget.service;

import com.alekseysamoylov.widget.model.LogList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;

import static com.alekseysamoylov.widget.constants.CustomLoggerConstants.*;
import static com.alekseysamoylov.widget.constants.DateTimeConstants.DATE_FORMAT_WITH_MS;
import static com.alekseysamoylov.widget.constants.MessageConstructorConstants.*;

/**
 * Message constructor for User interface.
 */
@Service
public class MessageConstructorService {

    private final LoggerService loggerService;

    @Autowired
    public MessageConstructorService(LoggerService loggerService) {
        this.loggerService = loggerService;
    }

    /**
     * Construct the message for user
     * @return message log info for a specified time
     */
    public String getMessage() {
        LogList logListForInterval = loggerService.getLastMessages();
        if (logListForInterval != null && !logListForInterval.getLogMessagesList().isEmpty()) {
            int info = 0;
            int warn = 0;
            int error = 0;
            for (String log : logListForInterval.getLogMessagesList()) {
                if (log.contains(INFO)) {
                    info++;
                } else if (log.contains(WARNING)) {
                    warn++;
                } else if (log.contains(ERROR)) {
                    error++;
                }
            }
            String formattedDate = new SimpleDateFormat(DATE_FORMAT_WITH_MS)
                    .format(logListForInterval.getLogMessagesDate());

            return String.format(MESSAGE_BODY, formattedDate, info, warn, error);
        } else {
            return null;
        }
    }
}
