package com.alekseysamoylov.widget.repository;

/**
 * Interval value repository
 */
public interface IntervalRepository {
    Integer findOne();

    void saveOne(Integer interval);
}
