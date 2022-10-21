package me.bega.parser;
import me.bega.gem.Gem;
import me.bega.validator.Validator;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
public class DOMParser {
    public static List<Gem> parseXML(String fileName) throws ParserConfigurationException, IOException, SAXException {

        if(!Validator.validateDocument(fileName)){
            return new ArrayList<>();
        }

        DocumentBuilder documentBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
        Document doc = documentBuilder.parse(new File(fileName));
        GemHandler handler = new GemHandler();

        NodeList gemListXML = doc.getElementsByTagName(GemHandler.getGEM());
        NodeList visualParametersListXML = doc.getElementsByTagName(GemHandler.getPARAMS());

        List<Gem> gemList = new ArrayList<>();

        for(int i = 0; i < gemListXML.getLength(); i++){
            handler.parseNode(gemListXML.item(i));
            handler.parseNode(visualParametersListXML.item(i));

            handler.parseElementFinish();
        }

        try {
            gemList = handler.obtainResult();
            Collections.sort(gemList);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return gemList;
    }
}
