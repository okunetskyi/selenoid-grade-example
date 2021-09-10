package com.oleh.utils;


import lombok.Cleanup;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Utilities {

    private static Logger log = LogManager.getLogger(Utilities.class);
    /**
     * Loads property values from a file.
     *
     * @param propertyFileName The name of properties file to be loaded
     * @return Properties
     */
    public static Properties readFromPropertyFile(String propertyFileName) {
        Properties prop = new Properties();
        try(InputStream in = Utilities.class.getClassLoader().getResourceAsStream(propertyFileName);) {

            prop.load(in);
        } catch (IOException ex) {
            log.error("Unable to open property file: " + ex.getMessage());
        }
        return prop;
    }

    /**
     * Writes property values to a property file. This will appends new values to the existing
     * file since some of the value is entered via maven.
     *
     * @param properties A Key Value map of data to be written to a file
     * @param filePath   the path to the file to be written
     * @param append     if true, then bytes will be written to the end of the file rather than the beginning
     */
    public static void writeToPropertyFile(Properties properties, String filePath, boolean append) {
        try {
            @Cleanup FileOutputStream fileOut = new FileOutputStream(filePath, append);
            properties.store(fileOut, null);
        } catch (FileNotFoundException fe) {
            log.error("Unable to locate the property file: " + fe.getMessage());
        } catch (IOException ie) {
            log.error(String.format("Unable to write to file %s: ", filePath) + ie.getMessage());
        } catch (Exception ex) {
            log.error("Unspecified error occurred while writing to a file: " + ex.getMessage());
        }
    }
}
