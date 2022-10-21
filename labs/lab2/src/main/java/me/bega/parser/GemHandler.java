package me.bega.parser;
import me.bega.gem.*;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.Element;
import org.xml.sax.Attributes;
import org.xml.sax.helpers.DefaultHandler;
import javax.xml.namespace.QName;
import javax.xml.stream.events.EndElement;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;
import java.util.ArrayList;
import java.util.List;

public class GemHandler extends DefaultHandler{
    private static final String GEM = "gem";
    private static final String PARAMS = "visualParameters";
    private static final String NAME = "name";
    private static final String PRECIOUSNESS = "preciousness";
    private static final String ORIGIN = "origin";
    private static final String COLOR = "color";
    private static final String OPACITY = "opacity";
    private static final String EDGING = "edging";
    private static final String VALUE = "value";
    private static final String ID = "id";

    private final List<Gem> gemList;
    private Gem gem;
    private VisualParameters visualParameters;
    private int currentOption;

    public GemHandler() {
        gemList = new ArrayList<>();
        currentOption = 0;
    }

    public static String getGEM() {
        return GEM;
    }

    public static String getPARAMS() {
        return PARAMS;
    }

    public List<Gem> obtainResult(){
        List<Gem> temp = new ArrayList<>(gemList);
        gemList.clear();
        return temp;
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attrs) {
        setOption(qName, attrs.getValue(0));
    }

    public void setOption(String option, String attribute){

        if (option.equals(GEM)) {
            gem = new Gem();
            visualParameters = new VisualParameters();
            gem.setId(attribute);
        }

        switch (option) {
            case NAME:
                currentOption = 1;
                break;
            case PRECIOUSNESS:
                currentOption = 2;
                break;
            case ORIGIN:
                currentOption = 3;
                break;
            case COLOR:
                currentOption = 4;
                break;
            case OPACITY:
                currentOption = 5;
                break;
            case EDGING:
                currentOption = 6;
                break;
            case VALUE:
                currentOption = 7;
                break;
        }
    }

    @Override
    public void endElement(String uri, String localName, String qName) {

        if (qName.equals(GEM)) {
            gem.setVisualParameters(visualParameters);
            gemList.add(gem);
        }
    }

    @Override
    public void characters(char[] ch, int offset, int length) {
        String s = new String(ch, offset, length);
        setOptionValue(s);
    }

    public void setOptionValue(String s) {

        try {
            switch (currentOption) {
                case 1:
                    gem.setName(s);
                    break;
                case 2:
                    gem.setPreciousness(Boolean.parseBoolean(s));
                    break;
                case 3:
                    gem.setOrigin(s);
                    break;
                case 4:
                    visualParameters.setColor(s);
                    break;
                case 5:
                    visualParameters.setOpacity(Integer.parseInt(s));
                    break;
                case 6:
                    visualParameters.setEdging(Integer.parseInt(s));
                    break;
                case 7:
                    gem.setValue(Double.parseDouble(s));
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        currentOption = 0;
    }

    public void parseNode(Node node){

        if(node.getAttributes().item(0) != null && node.getAttributes().item(0) != null){
            setOption(GEM, node.getAttributes().item(0).getTextContent());
        }

        NodeList childNodes = node.getChildNodes();
        for(int i = 0; i < childNodes.getLength(); i++){
            if(childNodes.item(i).getNodeType() == Node.ELEMENT_NODE) {
                Element element = (Element) childNodes.item(i);
                setOption(element.getTagName(), "");
                setOptionValue(element.getTextContent());
            }
        }
    }

    public void parseElementFinish(){
        endElement("", "", GEM);
    }

    public boolean parseXMLEventStart(XMLEvent xmlEvent){

        StartElement startElement = xmlEvent.asStartElement();
        String idPart = startElement.getName().getLocalPart();

        if(idPart.equals(GEM)) {
            setOption(startElement.getName().getLocalPart(), startElement.getAttributeByName(new QName(ID)).getValue());
            return false;
        } else {
            setOption(startElement.getName().getLocalPart(), "");
            return true;
        }

    }

    public void parseXMLEventValue(XMLEvent xmlEvent){
        if(xmlEvent.isCharacters()){
            setOptionValue(xmlEvent.asCharacters().getData());
        }
    }

    public void parseXMLEventFinish(XMLEvent xmlEvent){
        EndElement endElement = xmlEvent.asEndElement();
        if (endElement.getName().getLocalPart().equals(GEM)) {
            endElement("", "", GEM);
        }

    }
}
