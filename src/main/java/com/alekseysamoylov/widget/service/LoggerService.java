package com.alekseysamoylov.widget.service;

import com.alekseysamoylov.widget.model.LogList;
import com.alekseysamoylov.widget.repository.FilePathRepository;
import com.alekseysamoylov.widget.repository.IntervalRepository;
import com.alekseysamoylov.widget.repository.LogMessagesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 * Service for change log file in runtime
 */
@Service
public class LoggerService  {

    private final IntervalRepository intervalRepository;
    private final FilePathRepository filePathRepository;
    private final LogMessagesRepository logMessagesRepository;

    @Autowired
    public LoggerService(IntervalRepository intervalRepository, FilePathRepository filePathRepository, LogMessagesRepository logMessagesRepository) {
        this.intervalRepository = intervalRepository;
        this.filePathRepository = filePathRepository;
        this.logMessagesRepository = logMessagesRepository;
    }

    public LogList getLastMessages() {
        Integer interval = intervalRepository.findOne();
        String filePath = filePathRepository.findOne();
        return logMessagesRepository.findLast(interval, filePath);
    }

}
