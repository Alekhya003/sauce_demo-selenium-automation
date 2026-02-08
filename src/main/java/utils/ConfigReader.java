package utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ConfigReader {
    private static Properties properties;

    static {
        properties = new Properties();
        try{
            InputStream input = ConfigReader.class.getClassLoader().getResourceAsStream("config.properties");
            if(input== null){
                throw new RuntimeException("config.properties file does not exist");
            }
            properties.load(input);
        } catch (Exception e) {
            throw new RuntimeException("Failed to load config.properties file.");
        }
    }
    public static String getProperty(String key){
        return properties.getProperty(key);
    }
}
