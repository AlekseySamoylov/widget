package com.alekseysamoylov.widget.controller.rest;

import com.alekseysamoylov.widget.service.MessageConstructorService;
import com.alekseysamoylov.widget.service.PropertiesService;
import com.alekseysamoylov.widget.utils.CustomLogger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


/**
 * Controller for log information
 */
@RestController
@RequestMapping(value = "/rest")
public class WidgetController {

    private static final CustomLogger LOGGER = new CustomLogger();

    private final PropertiesService propertiesService;
    private final MessageConstructorService messageConstructorService;

    @Autowired
    public WidgetController(PropertiesService propertiesService, MessageConstructorService messageConstructorService) {
        this.propertiesService = propertiesService;
        this.messageConstructorService = messageConstructorService;
    }

    @RequestMapping(value = "/interval")
    public Integer getInterval() {
        LOGGER.info("Send interval");
        return propertiesService.getInterval();
    }

    @RequestMapping(value = "/interval", method = RequestMethod.POST)
    public void setInterval(@RequestBody Integer interval) {
        LOGGER.info("Receive new interval");
        propertiesService.setInterval(interval);
    }

    @RequestMapping(value = "/log", method = RequestMethod.GET, produces = "text/plain")
    public String getLastLogMessages() {
        LOGGER.info("Send message");
        return messageConstructorService.getMessage();
    }

    @RequestMapping(value = "/setInterval/{interval}")
    public void setInterval(@PathVariable Long interval) {
        LOGGER.info("Get new interval " + interval);
    }


}
