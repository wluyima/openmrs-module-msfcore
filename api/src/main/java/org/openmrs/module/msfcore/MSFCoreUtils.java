package org.openmrs.module.msfcore;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import org.apache.commons.lang3.StringUtils;

public class MSFCoreUtils {
    public static void overWriteFile(File file, String content) throws IOException {
        if (StringUtils.isNotBlank(content)) {
            FileWriter fooWriter = new FileWriter(file, false);
            fooWriter.write(content);
            fooWriter.close();
        }
    }

    public static boolean isValidUrl(String urlString) {
        return generateUrl(urlString) != null;
    }

    public static URL generateUrl(String uri) {
        try {
            return new URL(uri);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
