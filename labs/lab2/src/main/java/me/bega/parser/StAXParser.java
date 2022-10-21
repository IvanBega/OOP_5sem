package me.bega.parser;

import me.bega.gem.Gem;
import me.bega.validator.Validator;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.XMLEvent;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class StAXParser {

    public static List<Gem> parseXML(String fileName) throws FileNotFoundException, XMLStreamException {

        if(!Validator.validateDocument(fileName)){
            return new ArrayList<>();
        }

        List<Gem> gemList;
        XMLEventReader reader = XMLInputFactory.newInstance().createXMLEventReader(new FileInputStream(fileName));
        GemHandler handler = new GemHandler();

        while(reader.hasNext()) {
            XMLEvent xmlEvent = reader.nextEvent();
            if(xmlEvent.isStartElement()) {
                if(handler.parseXMLEventStart(xmlEvent)){
                    xmlEvent = reader.nextEvent();
                }
                handler.parseXMLEventValue(xmlEvent);
            }

            if(xmlEvent.isEndElement()) {
                handler.parseXMLEventFinish(xmlEvent);
            }
        }

        gemList = handler.obtainResult();
        Collections.sort(gemList);

        return gemList;
    }
}