package com.alekseysamoylov.widget.repository;

import com.alekseysamoylov.widget.model.LogList;

import java.util.List;
import java.util.NavigableMap;

/**
 * Log messages repository
 */
public interface LogMessagesRepository {

    /**
     * Find all log records in file
     * @return
     */
    NavigableMap<Long, String> findAll(String filePath);

    /**
     * Find last log records in the interval in current file
     * @param interval
     * @param filePath
     * @return
     */
    LogList findLast(Integer interval, String filePath);
}
