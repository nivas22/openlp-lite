package org.openlp.lite.handler;

import android.util.Log;

import org.apache.commons.lang3.StringUtils;
import org.openlp.lite.domain.Verse;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 * @Author : Madasamy
 * @Version : 1.0
 */
public class VerseHandler extends DefaultHandler
{
    private Stack<String> elementStack = new Stack<String>();
   // private Stack<Attributes> attributesStack = new Stack<Attributes>();
    private List<Verse> verseList = new ArrayList<Verse>();

    public List<Verse> getVerseList()
    {
        return verseList;
    }

    // we push current element name:
    @Override
    public void startElement(String uri, String localName,
                             String qName, Attributes attrs) throws SAXException
    {
        elementStack.push(qName);
     //   attributesStack.push(attrs);
    }

    @Override
    public void endElement(String namespaceURI, String localName,
                           String qName) throws SAXException
    {
        elementStack.pop();
       // attributesStack.pop();
    }

    // this method will be called for each character-section occurred;
    // if the element containes several CDATA-sections mixed with
    // child elements the number of calls depends on SAX-implementation:
    @Override
    public void characters(char[] ch, int start, int length)
            throws SAXException
    {
        String cdata = new String(ch, start, length);
       // Attributes attributes = attributesStack.peek();
        String element = elementStack.peek();
        if (element.equalsIgnoreCase("verse") && StringUtils.isNotBlank(cdata)) {
            Verse verse = new Verse();
         //   verse.setType(attributes.getValue("type"));
         //   verse.setLabel(Integer.parseInt(attributes.getValue("label")));
            verse.setContent(cdata);
            //Log.d(this.getClass().getName(),"CDATA:"+cdata);
            verseList.add(verse);
        }
    }
}
