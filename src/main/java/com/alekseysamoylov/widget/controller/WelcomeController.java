package com.alekseysamoylov.widget.controller;

import com.alekseysamoylov.widget.utils.CustomLogger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Map;

/**
 * Controller to show web page
 */
@Controller
public class WelcomeController {
    private static final CustomLogger LOGGER = new CustomLogger();


    @Value("${welcome.message}")
    private String message;

    @RequestMapping(value = "/")
    public String goWelcome(Map<String, Object> model) {
        LOGGER.info("Open welcome page " + message);
        model.put("message", this.message);
        return "index";
    }
}
