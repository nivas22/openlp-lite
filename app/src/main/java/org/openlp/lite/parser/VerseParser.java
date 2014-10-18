package org.openlp.lite.parser;

import android.content.Context;
import android.util.Log;

import org.apache.maven.shared.utils.io.FileUtils;
import org.openlp.lite.domain.Verse;
import org.openlp.lite.handler.VerseHandler;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

/**
 * @Author : Madasamy
 * @Version : 1.0
 */
public class VerseParser
{
    public List<Verse> parseVerse(Context context, String content)
    {
        List<Verse> verses = new ArrayList<Verse>();

        File xmlFile = null;
        try {
            File externalCacheDir = context.getExternalCacheDir();
            xmlFile = File.createTempFile("verse", "xml", externalCacheDir);
            Log.d(this.getClass().getName(), xmlFile.getAbsolutePath() + "created successfully");
            FileUtils.fileWrite(xmlFile, "", content);
            SAXParserFactory factory = SAXParserFactory.newInstance();
            factory.setValidating(true);
            SAXParser parser = factory.newSAXParser();
            VerseHandler verseHandler = new VerseHandler();
            parser.parse(xmlFile, verseHandler);
            verses.addAll(verseHandler.getVerseList());
        } catch (Exception ex) {
            Log.e(this.getClass().getName(), "Error occurred while parsing verse", ex);
        } finally {
            xmlFile.deleteOnExit();
        }
        return verses;
    }
}
