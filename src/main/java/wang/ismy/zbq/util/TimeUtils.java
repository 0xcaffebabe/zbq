package wang.ismy.zbq.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class TimeUtils {

    public static String getStrTime(){

        return LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME);
    }
}
