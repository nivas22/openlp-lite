package org.openlp.lite;


import org.apache.commons.io.FileUtils;

import java.io.File;

/**
 * Created by Seenivasan on 10/26/2014.
 */
public final class CommonConstants {
    public static final String CUSTOM_TAG_TEMP_FILENAME="custom-tag.properties";
    public static final String CUSTOM_TAG_FILE_EXTENSION="properties";
    public static final String TAG_PATTERN  = "\\{[\\w,\\W,\\d,\\D]\\}";




    public static File getTagFile(File tagFile) {
        try{
            if(!tagFile.exists()){
                FileUtils.touch(tagFile);
            }
        }
        catch (Exception ex){

        }
    return tagFile;
    }

}
