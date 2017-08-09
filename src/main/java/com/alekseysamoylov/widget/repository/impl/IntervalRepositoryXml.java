package com.alekseysamoylov.widget.repository.impl;

import com.alekseysamoylov.widget.repository.IntervalRepository;
import com.alekseysamoylov.widget.service.FileChangeScanner;
import com.alekseysamoylov.widget.utils.CustomLogger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import java.io.File;
import java.io.IOException;

import static com.alekseysamoylov.widget.constants.FileScannerConstants.CONFIGURATION_FILE_PATH;
import static com.alekseysamoylov.widget.constants.FileScannerConstants.INTERVAL_ELEMENT_EXPRESSION;

/**
 * Implementation {@link IntervalRepository}
 */
@Repository
public class IntervalRepositoryXml implements IntervalRepository {

    private static final CustomLogger LOGGER = new CustomLogger();

    private final FileChangeScanner fileChangeScanner;

    @Autowired
    public IntervalRepositoryXml(FileChangeScanner fileChangeScanner) {
        this.fileChangeScanner = fileChangeScanner;
    }

    @Override
    public Integer findOne() {
        return fileChangeScanner.getInterval();
    }

    @Override
    public void saveOne(Integer interval) {

        try {
            LOGGER.info("Start to change the interval in xml file");
            String filepath = CONFIGURATION_FILE_PATH;
            XPath xPath = XPathFactory.newInstance().newXPath();

            DocumentBuilderFactory docFactory = DocumentBuilderFactory
                    .newInstance();
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
            Document doc = docBuilder.parse(filepath);

            NodeList intervalNodeList = (NodeList) xPath.compile(INTERVAL_ELEMENT_EXPRESSION)
                    .evaluate(doc, XPathConstants.NODESET);

            Node intervalNode = intervalNodeList.item(0);

            intervalNode.setTextContent(String.valueOf(interval));

            // write the content into xml file
            TransformerFactory transformerFactory = TransformerFactory
                    .newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(new File(filepath));
            transformer.transform(source, result);

            LOGGER.info("Changing the interval in xml file is completed.");

        } catch (ParserConfigurationException | IOException | SAXException | TransformerException | XPathExpressionException e) {
            LOGGER.error(e.getMessage());
        }
    }
}
