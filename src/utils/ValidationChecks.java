package utils;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

public class ValidationChecks {

    public static Boolean isDuringBusinessHours(LocalDateTime startTime, LocalDateTime endTime) {
        //ZoneId zoneEST = ZoneId.of("America/New_York");
        //LocalTime localTime = startTime.toLocalTime();
        //System.out.println(localTime.now(zoneEST));

        int localStartHourEST = ControlData.localToEST(startTime).getHour();
        int localEndHourEST = ControlData.localToEST(endTime).getHour();
        System.out.println("Local Starting Hour in EST: " + localStartHourEST);
        System.out.println("Local Ending Hour in EST: " + localEndHourEST);
        return localStartHourEST >= 8 && localEndHourEST <= 22;
    }

    public static Boolean isNotOverlapping(LocalDateTime startDateTime, LocalDateTime endDateTime, int customerID) {




        return true;
    }
}
