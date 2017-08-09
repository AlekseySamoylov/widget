package com.alekseysamoylov.widget.service;

import com.alekseysamoylov.widget.repository.IntervalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;



/**
 * Service provides operation with widget properties
 */
@Service
public class PropertiesService {

    private final IntervalRepository intervalRepository;

    @Autowired
    public PropertiesService(IntervalRepository intervalRepository) {
        this.intervalRepository = intervalRepository;
    }

    /**
     * @return interval between info messages
     */
    public Integer getInterval() {
        return intervalRepository.findOne();
    }

    public void setInterval(Integer interval) {
        intervalRepository.saveOne(interval);
    }

}
