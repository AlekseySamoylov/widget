package com.alekseysamoylov.widget.service;

import com.alekseysamoylov.widget.exception.LogWidgetCriticalException;
import com.alekseysamoylov.widget.exception.LogWidgetException;
import com.alekseysamoylov.widget.utils.CustomLogger;
import com.alekseysamoylov.widget.utils.FileUtils;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.annotation.PreDestroy;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import static com.alekseysamoylov.widget.constants.FileScannerConstants.*;

@Service
public class FileChangeScanner {

    private static final CustomLogger LOGGER = new CustomLogger();

    private static volatile boolean applicationIsRunning = false;
    private static volatile String filePath = STANDARD_LOG_PATH;
    private volatile Integer interval = STANDARD_INTERVAL;
    private ExecutorService executorService;

    public static String getFilePathForLogger() {
        return filePath;
    }

    public String getFilePath() {
        checkToStart();
        return filePath;
    }

    public Integer getInterval() {
        checkToStart();
        return interval;
    }


    private void setInterval(Integer interval) {
        this.interval = interval;
    }

    /**
     * Stop the file scanning thread
     */
    @PreDestroy
    public void shutDown() {
        applicationIsRunning = false;
        executorService.shutdown();
        try {
            if (!executorService.awaitTermination(5, TimeUnit.SECONDS)) {
                LOGGER.warn("Still waiting after 5seconds: calling System.exit(0)...");

                System.exit(0);
            }
            executorService.shutdownNow();
        } catch (InterruptedException e) {
              System.exit(0);
        }
    }

    private void setFilePathToLogger(String filePath) throws LogWidgetException {
        createLogFile(filePath);
        this.filePath = filePath;
    }

    private void checkToStart() {
        Path configurationFilePath = Paths.get(CONFIGURATION_FILE_PATH);
        if (!applicationIsRunning) {
            try {
                if (Files.exists(configurationFilePath)) {
                    fillLogWidget(configurationFilePath);
                } else {
                    createNewFile(configurationFilePath);
                }
            } catch (ParserConfigurationException | LogWidgetException e) {
                LOGGER.error("Something was wrong with first reading log-widget.xml " + e.getMessage());
            }
            synchronized (this) {
                if (!applicationIsRunning) {
                    applicationIsRunning = true;

                    executorService = Executors.newSingleThreadExecutor();

                    executorService.execute(() -> {
                        try {
                            getFileToSearchChangesParseAndFillIpList(configurationFilePath);
                        } catch (IOException | InterruptedException | LogWidgetException | ParserConfigurationException e) {
                            applicationIsRunning = false;
                            LOGGER.error("Something was wrong in working with Log widget properties " + e.getMessage());
                            throw new LogWidgetCriticalException("Something was wrong in working with Log widget properties ", e);
                        }
                    });
                }
            }
        }

        if (!Files.exists(configurationFilePath)) {
            applicationIsRunning = false;
        }

    }

    private void fillFieldsFromDocument(Document document) throws LogWidgetException {

        XPath xPath = XPathFactory.newInstance().newXPath();
        try {
            NodeList pathNodeList = (NodeList) xPath.compile(PATH_ELEMENT_EXPRESSION)
                    .evaluate(document, XPathConstants.NODESET);
                Node pathNode = pathNodeList.item(0);

                if (pathNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element eElement = (Element) pathNode;
                    String path = eElement
                            .getTextContent();
                    setFilePathToLogger(path);
                }

            NodeList intervalNodeList = (NodeList) xPath.compile(INTERVAL_ELEMENT_EXPRESSION)
                    .evaluate(document, XPathConstants.NODESET);
                Node intervalNode = intervalNodeList.item(0);

                if (intervalNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element eElement = (Element) intervalNode;
                    Integer interval = Integer.valueOf(eElement
                            .getTextContent());
                    setInterval(interval);
                }

        } catch (XPathExpressionException e) {
            LOGGER.error(e.getMessage());
        }
    }

    private void getFileToSearchChangesParseAndFillIpList(Path logWidgetPath)
            throws IOException, InterruptedException, LogWidgetException, ParserConfigurationException {

        try (final WatchService watchService = FileSystems.getDefault().newWatchService()) {
            Path parentDirectories = logWidgetPath.getParent();
            if (parentDirectories != null) {
                logWidgetPath.getParent().register(watchService, StandardWatchEventKinds.ENTRY_MODIFY);
            }
            while (applicationIsRunning) {
                final WatchKey innerWatchKey = watchService.take();
                for (WatchEvent<?> event : innerWatchKey.pollEvents()) {
                    //we only register "ENTRY_MODIFY" so the context is always a Path.
                    final Path changed = (Path) event.context();
                    String logWidgetFileName = logWidgetPath.getFileName().toString();
                    if (changed.endsWith(logWidgetFileName)) {
                        LOGGER.info("Logger.info: log-widget.xml was changed. " +
                                LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE));
                        fillLogWidget(logWidgetPath);
                    }
                }
                // reset the key
                boolean valid = innerWatchKey.reset();
                if (!valid) {
                    LOGGER.error("Key has been unregisterede");
                }
            }
            if (!applicationIsRunning) {
                Thread.currentThread().interrupt();
            }
        }
    }

    private void fillLogWidget(Path logWidgetPath) throws ParserConfigurationException, LogWidgetException {
        DocumentBuilderFactory documentBuilderFactory
                = DocumentBuilderFactory.newInstance();
        DocumentBuilder documentBuilder;

        documentBuilder = documentBuilderFactory.newDocumentBuilder();
        Optional<Document> documentOptional = Optional.empty();

        try (InputStream inputStream = Files.newInputStream(logWidgetPath)) {
            if (documentBuilder != null) {
                Document doc = documentBuilder.parse(inputStream);
                documentOptional = Optional.ofNullable(doc);
            } else {
                LOGGER.error("Cannot parse log-widget.xml file.");
            }
        } catch (IOException | SAXException e) {
            LOGGER.error("Cannot parse log-widget.xml file." + e.getMessage());
            throw new LogWidgetException("Logger.error: Cannot parse log-widget.xml file.", e);
        }
        if (documentOptional.isPresent()) {
            Document document = documentOptional.get();
            document.getDocumentElement().normalize();
            
            fillFieldsFromDocument(document);

            LOGGER.warn("Log widget properties readed from file: ");
            

        }
    }

    private void createNewFile(Path logWidgetPath) throws LogWidgetException {
        LOGGER.warn("Logger.debug: Try to create new log-widget.xml file");
        try {
            Path parentDirectories = logWidgetPath.getParent();
            if (parentDirectories != null) {
                Files.createDirectories(parentDirectories);
            }
            Files.createFile(logWidgetPath);
            try (BufferedWriter writer = Files.newBufferedWriter(logWidgetPath)) {
                writer.write(STANDARD_FILE_CONTENT);
            }
        } catch (IOException e) {
            throw new LogWidgetException("New file was not created", e);
        }
    }

    private void createLogFile(String logFilePath) throws LogWidgetException {
        LOGGER.warn("Logger.debug: Try to create new log file");
        try {
            Path path = Paths.get(logFilePath);
            FileUtils.createLogFile(path);
        } catch (IOException e) {
            throw new LogWidgetException("New Log file was not created", e);
        }
    }


}
