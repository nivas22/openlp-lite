package org.openlp.lite.utils;

import android.text.Spannable;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.widget.TextView;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * Created by Seenivasan on 10/26/2014.
 */
public final class PropertyUtils {

    public static void setProperties(Map<String, String> propertiesMap, File propertiesFile) {
        Properties properties = new Properties();
        OutputStream outputStream = null;
        try {
            outputStream = new FileOutputStream(propertiesFile);
            for (String key : propertiesMap.keySet()) {
                properties.setProperty(key, propertiesMap.get(key));
            }
            properties.store(outputStream, "");
        } catch (Exception ex) {

        }
    }

    public static void setProperty(String key, String value, File propertiesFile) {
        Map<String, String> propertyMap = new HashMap<String, String>();
        propertyMap.put(key, value);
        setProperties(propertyMap, propertiesFile);
    }

    public static String getProperty(String key, File propertiesFile) {
        Properties properties = new Properties();
        InputStream inputStream = null;
        try {
            inputStream = new FileInputStream(propertiesFile);
            properties.load(inputStream);
            return properties.get(key).toString();
        } catch (Exception ex) {
            return "";
        }
    }

    public static void appendColoredText(TextView tv, String text, int color) {
        Log.d(PropertyUtils.class.getName(), "Lines" + text);
        Log.d(PropertyUtils.class.getName(),"color"+color);
        int start = tv.getText().length();

        tv.append(text);
        tv.append("\n");
        int end = tv.getText().length();

        Spannable spannableText = (Spannable) tv.getText();
        spannableText.setSpan(new ForegroundColorSpan(color), start, end, 0);
    }
}
