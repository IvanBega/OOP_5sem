package me.bega.parser;

import me.bega.gem.Gem;
import me.bega.validator.Validator;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParserFactory;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SAXParser {

    public static List<Gem> parseXML(String fileName) throws IOException, SAXException, ParserConfigurationException {

        if(!Validator.validateDocument(fileName)){
            return new ArrayList<>();
        }

        List<Gem> gemList = new ArrayList<>();

        javax.xml.parsers.SAXParser parser = SAXParserFactory.newInstance().newSAXParser();
        GemHandler handler = new GemHandler();
        parser.parse(fileName, handler);

        gemList = handler.obtainResult();
        Collections.sort(gemList);

        return gemList;
    }
}