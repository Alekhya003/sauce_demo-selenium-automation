package utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * TestDataManager - Manages test data from external JSON files
 * Allows data-driven testing without hardcoding values in tests
 */
public class TestDataManager {
    private static final Logger log = LoggerFactory.getLogger(TestDataManager.class);
    private static final String TEST_DATA_DIR = "src/test/resources/testdata";
    private static final Gson gson = new GsonBuilder().setPrettyPrinting().create();
    
    /**
     * Load test data from JSON file
     */
    public static Map<String, Object> loadTestData(String filename) {
        try {
            String filepath = TEST_DATA_DIR + "/" + filename + ".json";
            FileReader reader = new FileReader(filepath);
            Type type = new TypeToken<Map<String, Object>>(){}.getType();
            Map<String, Object> data = gson.fromJson(reader, type);
            reader.close();
            log.info("Loaded test data from: {}", filepath);
            return data;
        } catch (IOException e) {
            log.error("Failed to load test data: {}", filename, e);
            throw new RuntimeException("Failed to load test data: " + filename, e);
        }
    }
    
    /**
     * Load test data list from JSON file
     */
    public static List<Map<String, Object>> loadTestDataList(String filename) {
        try {
            String filepath = TEST_DATA_DIR + "/" + filename + ".json";
            FileReader reader = new FileReader(filepath);
            Type type = new TypeToken<List<Map<String, Object>>>(){}.getType();
            List<Map<String, Object>> data = gson.fromJson(reader, type);
            reader.close();
            log.info("Loaded test data list from: {}", filepath);
            return data;
        } catch (IOException e) {
            log.error("Failed to load test data list: {}", filename, e);
            throw new RuntimeException("Failed to load test data list: " + filename, e);
        }
    }
    
    /**
     * Get specific value from test data
     */
    public static String getTestData(String filename, String key) {
        try {
            Map<String, Object> data = loadTestData(filename);
            Object value = data.get(key);
            if (value == null) {
                log.warn("Key '{}' not found in test data file: {}", key, filename);
                return "";
            }
            return value.toString();
        } catch (Exception e) {
            log.error("Failed to get test data value", e);
            return "";
        }
    }
    
    /**
     * Get nested value from test data using dot notation
     * Example: "user.credentials.username"
     */
    public static String getNestedTestData(String filename, String keyPath) {
        try {
            Map<String, Object> data = loadTestData(filename);
            String[] keys = keyPath.split("\\.");
            Object current = data;
            
            for (String key : keys) {
                if (current instanceof Map) {
                    current = ((Map<String, Object>) current).get(key);
                } else {
                    log.warn("Cannot navigate key path: {}", keyPath);
                    return "";
                }
            }
            
            return current != null ? current.toString() : "";
        } catch (Exception e) {
            log.error("Failed to get nested test data", e);
            return "";
        }
    }
}
