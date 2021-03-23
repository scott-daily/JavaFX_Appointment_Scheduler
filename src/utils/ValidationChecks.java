package utils;

import models.Appointment;
import java.time.*;

public class ValidationChecks {

    public static Boolean isDuringBusinessHours(LocalDateTime startTime, LocalDateTime endTime) {

        int localStartHourEST = ControlData.localToEST(startTime).getHour();
        int localEndHourEST = ControlData.localToEST(endTime).getHour();

        return localStartHourEST >= 8 && localEndHourEST <= 22;
    }

    public static Boolean isNotOverlapping(LocalDateTime startDateTime, LocalDateTime endDateTime, int customerID) {

        LocalDate startingDate = startDateTime.toLocalDate();
        //Appointment.refreshAppointments();

        for (Appointment appt : Appointment.appointmentsList) {
            if (appt.getCustomerID() == customerID) {
                LocalDate startingDateStoredAppt = appt.getStart().toLocalDateTime().toLocalDate();
                if (startingDate.equals(startingDateStoredAppt)) {
                    LocalTime startTime = startDateTime.toLocalTime();
                    LocalTime endTime = endDateTime.toLocalTime();
                    LocalTime savedStartTime = appt.getStart().toLocalDateTime().toLocalTime();
                    LocalTime savedEndTime = appt.getEnd().toLocalDateTime().toLocalTime();

                    if (startTime.isAfter(savedStartTime) && (endTime.isBefore(savedEndTime))) {
                        return false;
                    }

                    if (startTime.isBefore(savedStartTime) && (endTime.isBefore(savedEndTime))) {
                        return false;
                    }

                    if (startTime.isAfter(savedStartTime) && endTime.isAfter(savedEndTime) && startTime.isBefore(savedEndTime)) {
                        return false;
                    }

                    if (startTime.isBefore(savedStartTime) && endTime.isAfter(savedEndTime)) {
                        return false;
                    }
                }
            }
        }
        return true;
    }
}
