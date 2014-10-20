package org.openlp.lite.parser;

import android.content.Context;
import android.util.Log;


import org.apache.commons.io.FileUtils;
import org.openlp.lite.domain.Verse;
import org.openlp.lite.handler.VerseHandler;
import org.w3c.dom.CharacterData;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

/**
 * @Author : Madasamy
 * @Version : 1.0
 */
public class VerseParser
{
    File xmlFile = null;
    List<Verse> verses = new ArrayList<Verse>();

    public List<Verse> parseVerse(Context context, String content)
    {
        try {
            File externalCacheDir = context.getExternalCacheDir();
            xmlFile = File.createTempFile("verse", "xml", externalCacheDir);
            Log.d(this.getClass().getName(), xmlFile.getAbsolutePath() + "created successfully");
            FileUtils.write(xmlFile, content);
            Log.d(this.getClass().getName(), "XML Content" + FileUtils.readFileToString(xmlFile));
            SAXParserFactory factory = SAXParserFactory.newInstance();
            //            factory.setValidating(true);
            SAXParser parser = factory.newSAXParser();
            VerseHandler verseHandler = new VerseHandler();
            parser.parse(xmlFile, verseHandler);
            verses.addAll(verseHandler.getVerseList());
            return verses;
        } catch (Exception ex) {
            Log.e(this.getClass().getName(), "Error occurred while parsing verse", ex);
        } finally {
            xmlFile.deleteOnExit();
        }
        return verses;
    }

    public List<Verse> parseVerseDom(Context context, String content)
    {
        List<Verse> verses = new ArrayList<Verse>();
        try {
            File externalCacheDir = context.getExternalCacheDir();
            xmlFile = File.createTempFile("verse", "xml", externalCacheDir);
            Log.d(this.getClass().getName(), xmlFile.getAbsolutePath() + "created successfully");
            FileUtils.write(xmlFile, content);
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            factory.setCoalescing(true);
            Document doc = factory.newDocumentBuilder().parse(xmlFile);
            NodeList nodes = doc.getElementsByTagName("verse");
            for (int i = 0; i < nodes.getLength(); i++) {
                Element element = (Element) nodes.item(i);
                Verse verse = new Verse();
                verse.setLabel(Integer.parseInt(element.getAttribute("label")));
                verse.setContent(element.getAttribute("type"));
                verse.setContent(getCharacterDataFromElement(element));
                verses.add(verse);
            }
        } catch (Exception ex) {
            Log.e(this.getClass().getName(), "Error occurred while parsing verse", ex);
        } finally {
            xmlFile.deleteOnExit();
        }
        return verses;
    }

    public static String getCharacterDataFromElement(Element e)
    {
        Node child = e.getFirstChild();
        if (child instanceof CharacterData) {
            CharacterData cd = (CharacterData) child;
            return cd.getData();
        }
        return "";
    }
}
