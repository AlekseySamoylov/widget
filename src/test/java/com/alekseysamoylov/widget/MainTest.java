package com.alekseysamoylov.widget;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

import static com.alekseysamoylov.widget.constants.FileScannerConstants.CONFIGURATION_FILE_PATH;

/**
 * I didn't have enough time for writing test. But I can use TDD approach.
 */
public class MainTest {

//    public static void main(String argv[]) {
//
//        try {
//            String filepath = CONFIGURATION_FILE_PATH;
//            System.out.println(filepath);
//            DocumentBuilderFactory docFactory = DocumentBuilderFactory
//                    .newInstance();
//            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
//            Document doc = docBuilder.parse(filepath);
//
//            // Get the root element
//            Node data= doc.getFirstChild();
//
//            Node intervalNode = doc.getElementsByTagName("interval").item(0);
//
//            // I am not doing any thing with it just for showing you
//            String currentStartdate = intervalNode.getNodeValue();
//
//
//            intervalNode.setTextContent(String.valueOf(10));
//
//            // write the content into xml file
//            TransformerFactory transformerFactory = TransformerFactory
//                    .newInstance();
//            Transformer transformer = transformerFactory.newTransformer();
//            DOMSource source = new DOMSource(doc);
//            StreamResult result = new StreamResult(new File(filepath));
//            transformer.transform(source, result);
//
//            System.out.println("Done");
//
//        } catch (ParserConfigurationException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        } catch (TransformerException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        } catch (SAXException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        } catch (IOException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        }
//    }
}
