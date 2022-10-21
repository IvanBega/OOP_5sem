package me.bega.validator;

import org.xml.sax.SAXException;

import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import java.io.File;
import java.io.IOException;
import java.util.logging.Logger;

public class Validator {
    private static final Logger log = Logger.getLogger(Validator.class.getName());

    public static boolean validateDocument(String pathXML){
        return validateDocument(pathXML, pathXML.replace("xml", "xsd"));
    }

    public static boolean validateDocument(String pathXML, String pathXSD){
        try {
            SchemaFactory factory = SchemaFactory.newInstance("http://www.w3.org/2001/XMLSchema");
            Schema schema = factory.newSchema(new File(pathXSD));
            javax.xml.validation.Validator validator = schema.newValidator();
            validator.validate(new StreamSource(new File(pathXML)));
        }  catch (IOException | SAXException e) {
            log.info(e.getMessage());
            //System.out.println(e.getMessage());
            return false;
        }
        return true;
    }
}