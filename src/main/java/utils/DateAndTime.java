package utils;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public class DateAndTime {
    public String dateTime(){
        ZoneId zone = ZoneId.of("Asia/Kolkata");
        ZonedDateTime time = ZonedDateTime.now(zone);
        DateTimeFormatter format = DateTimeFormatter.ofPattern("dd/MM/yyyy 'T' HH:mm:ss");
        return time.format(format);
    }
}
