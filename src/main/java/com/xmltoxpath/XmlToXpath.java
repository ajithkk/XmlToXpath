package com.xmltoxpath;

import com.sun.org.apache.xalan.internal.xsltc.trax.TransformerFactoryImpl;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import javax.xml.xpath.XPathExpressionException;
import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.net.URL;
import java.util.Arrays;
import java.util.TreeSet;

public class XmlToXpath {

    public static void main(String[] args) throws ParserConfigurationException, IOException, SAXException, XPathExpressionException, TransformerException {

        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        documentBuilderFactory.setNamespaceAware(true);
        DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();

        Document document = documentBuilder.parse(getXml("service.xml"));

        File file = new File(getXml("Xml2XPath.xslt"));
        TransformerFactory transformerFactory = new TransformerFactoryImpl();
        StreamSource streamSource = new StreamSource(file);
        Transformer transformer = transformerFactory.newTransformer(streamSource);
        StringWriter s = new StringWriter();
        transformer.transform(new DOMSource(document), new StreamResult(s));
        TreeSet<String> allXpath = new TreeSet<>();
        allXpath.addAll(Arrays.asList(s.getBuffer().toString().split("\n")));
        for (String xpath : allXpath) {
            System.out.println(xpath);
        }

    }

    static String getXml(String fileName) {

        ClassLoader classLoader = new XmlToXpath().getClass().getClassLoader();
        URL resource = classLoader.getResource(fileName);
        return resource.getPath();


    }


}
