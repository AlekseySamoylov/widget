package com.alekseysamoylov.widget.exception;


public class LogWidgetCriticalException extends RuntimeException {

    public LogWidgetCriticalException(String message, Throwable e) {
        super(message, e);
    }
}
