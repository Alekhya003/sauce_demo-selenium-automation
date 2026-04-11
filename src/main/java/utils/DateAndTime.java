package utils;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * DateAndTime - Utility for date/time operations
 * Supports multiple time zones and formats
 */
public class DateAndTime {
    private static final Logger log = LoggerFactory.getLogger(DateAndTime.class);
    private static final ZoneId ZONE = ZoneId.of("Asia/Kolkata");
    
    /**
     * Get current date and time in India timezone
     */
    public String dateTime() {
        return getCurrentDateTime();
    }
    
    /**
     * Get current date and time (static method)
     */
    public static String getCurrentDateTime() {
        try {
            ZonedDateTime time = ZonedDateTime.now(ZONE);
            DateTimeFormatter format = DateTimeFormatter.ofPattern("dd/MM/yyyy 'T' HH:mm:ss");
            return time.format(format);
        } catch (Exception e) {
            log.error("Failed to get current date time", e);
            return "N/A";
        }
    }
    
    /**
     * Get current timestamp in milliseconds
     */
    public static long getCurrentTimestamp() {
        return System.currentTimeMillis();
    }
    
    /**
     * Get current time in ISO format
     */
    public static String getCurrentTimeISO() {
        try {
            ZonedDateTime time = ZonedDateTime.now(ZONE);
            DateTimeFormatter format = DateTimeFormatter.ISO_OFFSET_DATE_TIME;
            return time.format(format);
        } catch (Exception e) {
            log.error("Failed to get ISO time", e);
            return "N/A";
        }
    }
    
    /**
     * Get current date only
     */
    public static String getCurrentDate() {
        try {
            ZonedDateTime time = ZonedDateTime.now(ZONE);
            DateTimeFormatter format = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            return time.format(format);
        } catch (Exception e) {
            log.error("Failed to get current date", e);
            return "N/A";
        }
    }
    
    /**
     * Get current time only
     */
    public static String getCurrentTime() {
        try {
            ZonedDateTime time = ZonedDateTime.now(ZONE);
            DateTimeFormatter format = DateTimeFormatter.ofPattern("HH:mm:ss");
            return time.format(format);
        } catch (Exception e) {
            log.error("Failed to get current time", e);
            return "N/A";
        }
    }
}
