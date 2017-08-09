package com.alekseysamoylov.widget.constants;


public class FileScannerConstants {
    public static final String CONFIGURATION_FILE_PATH = System.getProperty("java.io.tmpdir") + "/configuration.xml";
    public static final String PATH_ELEMENT_EXPRESSION = "/log-widget/path";
    public static final String INTERVAL_ELEMENT_EXPRESSION = "/log-widget/interval";
    public static final String STANDARD_LOG_PATH = System.getProperty("java.io.tmpdir") + "/logWidget.log";
    public static final Integer STANDARD_INTERVAL = 5;
    public static final String STANDARD_FILE_CONTENT = "<?xml version=\"1.0\" encoding=\"utf-8\"?>" +
            "<log-widget>" +
            "<path>" + STANDARD_LOG_PATH + "</path>" +
            "<interval>" + STANDARD_INTERVAL + "</interval>" +
            "</log-widget>";

}
