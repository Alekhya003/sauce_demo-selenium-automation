package utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * ConfigReader - Loads and provides access to configuration properties
 * Supports both config.properties file and environment variables
 * Environment variables take precedence over properties file
 */
public class ConfigReader {
    private static final Logger log = LoggerFactory.getLogger(ConfigReader.class);
    private static Properties properties;
    
    static {
        loadProperties();
    }
    
    /**
     * Load properties from config.properties file
     */
    private static void loadProperties() {
        try {
            properties = new Properties();
            InputStream input = ConfigReader.class.getClassLoader().getResourceAsStream("config.properties");
            
            if (input == null) {
                log.warn("config.properties file not found in classpath. Using environment variables only.");
                properties = new Properties();
            } else {
                properties.load(input);
                input.close();
                log.info("Successfully loaded config.properties");
            }
        } catch (IOException e) {
            log.error("Failed to load config.properties file", e);
            throw new RuntimeException("Failed to load configuration file: " + e.getMessage(), e);
        }
    }
    
    /**
     * Get property value with environment variable override
     * Environment variables take precedence: property_name -> PROPERTY_NAME (env var)
     */
    public static String getProperty(String key) {
        if (key == null || key.isEmpty()) {
            throw new IllegalArgumentException("Property key cannot be null or empty");
        }
        
        // Check environment variable first (with uppercase and underscore conversion)
        String envVarName = key.toUpperCase().replace(".", "_");
        String envValue = System.getenv(envVarName);
        
        if (envValue != null && !envValue.isEmpty()) {
            log.debug("Property '{}' loaded from environment variable: {}", key, envVarName);
            return envValue;
        }
        
        // Fall back to properties file
        String propertyValue = properties.getProperty(key);
        
        if (propertyValue == null) {
            log.warn("Property '{}' not found in config or environment variables", key);
            return "";
        }
        
        log.debug("Property '{}' loaded from config.properties", key);
        return propertyValue;
    }
    
    /**
     * Get property with default value if not found
     */
    public static String getProperty(String key, String defaultValue) {
        String value = getProperty(key);
        return value.isEmpty() ? defaultValue : value;
    }
    
    /**
     * Get property as integer
     */
    public static int getPropertyAsInt(String key, int defaultValue) {
        try {
            String value = getProperty(key);
            return value.isEmpty() ? defaultValue : Integer.parseInt(value);
        } catch (NumberFormatException e) {
            log.warn("Failed to parse property '{}' as integer, using default: {}", key, defaultValue);
            return defaultValue;
        }
    }
    
    /**
     * Get property as boolean
     */
    public static boolean getPropertyAsBoolean(String key, boolean defaultValue) {
        String value = getProperty(key);
        if (value.isEmpty()) {
            return defaultValue;
        }
        return Boolean.parseBoolean(value);
    }
    
    /**
     * Check if property exists
     */
    public static boolean propertyExists(String key) {
        String envVarName = key.toUpperCase().replace(".", "_");
        return System.getenv(envVarName) != null || properties.containsKey(key);
    }
}
