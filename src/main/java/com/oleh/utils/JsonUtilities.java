package com.oleh.utils;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public final class JsonUtilities {

    private static Logger log = LogManager.getLogger(JsonUtilities.class);

    private JsonUtilities() {
    }

//    private static JSONObject getJSONFromCSV(String filePath) {
//        JSONObject jsonObj = null;
//        try (FileReader fr = new FileReader(filePath)) {
//            JSONTokener tokener = new JSONTokener(fr);
//            jsonObj = new JSONObject(tokener);
//        } catch (Exception e) {
//            log.error("Error occurred while fetching JSON from file");
//            log.error(e.getMessage());
//            // No need to throw the exception, this method is used in Constants file
//        }
//        return jsonObj;
//    }

//    @Deprecated
//    public static void writeJsonFromObject(JSONObject jsonObject, String filePath) throws Exception {
//        try (BufferedWriter bf = new BufferedWriter(new FileWriter(filePath))) {
//            jsonObject.write(bf, 1, 1);
//        } catch (Exception e) {
//            log.error("Error occurred while writing JSON");
//            log.error(e.getMessage());
//            throw e;
//        }
//    }
//
//    public static Object readJSONFromObj(String filePath, String... key) {
//        JSONObject jsonObj = getJSONFromCSV(filePath);
//        for (int i = 0; i < key.length - 1; i++) {
//            jsonObj = (JSONObject) jsonObj.get(key[i]);
//        }
//        return jsonObj.get(key[key.length - 1]);
//    }
//
    public static <T> T parseJsonFromFile(String filePath, Class<T> clazz) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            String json = new String(Files.readAllBytes(Paths.get(filePath)));
            return objectMapper.readValue(json, clazz);
        } catch (IOException e) {
            log.error("Failed to read file [" + filePath + "]", e);
            throw new RuntimeException(e);
        }
    }
//
//    public static String getJsonFromObjectUpperCaseFields(Object object) {
//        try {
//            ObjectMapper mapper = new ObjectMapper();
//            mapper.setPropertyNamingStrategy(PropertyNamingStrategy.UPPER_CAMEL_CASE);
//            return mapper.writeValueAsString(object);
//        } catch (JsonProcessingException e) {
//            log.error(e.getStackTrace());
//            throw new RuntimeException(e);
//        }
//    }
//
//    public static String getJsonFromObject(Object object) {
//        try {
//            ObjectMapper mapper = new ObjectMapper();
//            return mapper.writeValueAsString(object);
//        } catch (JsonProcessingException e) {
//            log.error(e.getStackTrace());
//            throw new RuntimeException(e);
//        }
//    }
}
