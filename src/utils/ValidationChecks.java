package utils;

import DBLink.AppointmentsLink;
import models.Appointment;

import java.sql.Timestamp;
import java.time.*;

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

        LocalDate startingDate = startDateTime.toLocalDate();
        Appointment.refreshAppointments();

        for (Appointment appt : Appointment.appointmentsList) {
            if (appt.getCustomerID() == customerID) {
                System.out.println("matching appointment: " + appt.getCustomerID());
                LocalDate startingDateStoredAppt = appt.getStart().toLocalDateTime().toLocalDate();
                if (startingDate.equals(startingDateStoredAppt)) {
                    System.out.println("Same dates");
                    LocalTime startTime = startDateTime.toLocalTime();
                    LocalTime endTime = endDateTime.toLocalTime();
                    LocalTime savedStartTime = appt.getStart().toLocalDateTime().toLocalTime();
                    LocalTime savedEndTime = appt.getEnd().toLocalDateTime().toLocalTime();
                    System.out.println("Submitted start time: " + startTime);
                    System.out.println("Submitted end time: " + endTime);
                    System.out.println("Saved start time: " + savedStartTime);
                    System.out.println("Saved end time: " + savedEndTime);
                }
            }

                    /*int startHour = startDateTime.getHour();
                    int endHour = endDateTime.getHour();
                    int savedStartHour = appt.getStart().toLocalDateTime().getHour();
                    int savedEndHour = appt.getEnd().toLocalDateTime().getHour();
                    int startMinute = startDateTime.getMinute();
                    int endMinute = endDateTime.getMinute();
                    int savedStartMinute = appt.getStart().toLocalDateTime().getMinute();
                    int savedEndMinute = appt.getEnd().toLocalDateTime().getMinute();
                }
            }
            return true;*/
        }
        return true;
    }
}
