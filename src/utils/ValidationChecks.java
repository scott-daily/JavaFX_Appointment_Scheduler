package utils;

import models.Appointment;
import java.time.*;

/**
 * Class used to store all validation check methods.
 */
public class ValidationChecks {
    /**
     * Method to cehck if a starting and ending LocalDateTime are during EST business hours.
     * @param startTime The starting time and date.
     * @param endTime The ending time and date.
     * @return A boolean value indicating whether or not the times are during business hours.
     */
    public static Boolean isDuringBusinessHours(LocalDateTime startTime, LocalDateTime endTime) {

        int localStartHourEST = ControlData.localToEST(startTime).getHour();
        int localEndHourEST = ControlData.localToEST(endTime).getHour();
        int localStartMinuteEST = ControlData.localToEST(startTime).getMinute();
        int localEndMinuteEST = ControlData.localToEST(endTime).getMinute();

        if (localStartHourEST >= 8 && localEndHourEST < 22 && localStartHourEST < 22) {
            return true;
        } else if (localStartHourEST >= 8 && localEndHourEST == 22) {
            return localEndMinuteEST == 0;
        } else {
            return false;
        }
    }

    /**
     * Method to check to see whether or not two appointments have overlapping start and end times with an already saved appointment.
     * @param startDateTime The start time of the appointment to check.
     * @param endDateTime The end time of the appointment to check.
     * @param customerID The customer ID to see if that customer has any appointments saved that conflict with the new appointment.
     * @return A boolean indicating whether the appointments overlap with any of the customers other appointments.
     */
    public static Boolean isNotOverlapping(LocalDateTime startDateTime, LocalDateTime endDateTime, int customerID) {

        LocalDate startingDate = startDateTime.toLocalDate();

        for (Appointment appt : Appointment.appointmentsList) {
            if (appt.getCustomerID() == customerID) {
                if (appt.getAppointmentID() != ControlData.appointmentID) {
                    LocalDate startingDateStoredAppt = appt.getStart().toLocalDateTime().toLocalDate();
                    if (startingDate.equals(startingDateStoredAppt)) {
                        LocalTime startTime = startDateTime.toLocalTime();
                        LocalTime endTime = endDateTime.toLocalTime();
                        LocalTime savedStartTime = appt.getStart().toLocalDateTime().toLocalTime();
                        LocalTime savedEndTime = appt.getEnd().toLocalDateTime().toLocalTime();

                        if (startTime.isAfter(savedStartTime) && endTime.isBefore(savedEndTime)) {
                            return false;
                        }

                        if (startTime.isBefore(savedStartTime) && endTime.isBefore(savedEndTime) && endTime.isAfter(savedStartTime)) {
                            return false;
                        }

                        if (startTime.equals(savedStartTime) && endTime.equals(savedEndTime)) {
                            return false;
                        }

                        if (startTime.isBefore(savedStartTime) && endTime.equals(savedEndTime)) {
                            return false;
                        }

                        if (startTime.isAfter(savedStartTime) && endTime.equals(savedEndTime)) {
                            return false;
                        }

                        if (startTime.isAfter(savedStartTime) && endTime.isAfter(savedEndTime) && startTime.isBefore(savedEndTime)) {
                            return false;
                        }

                        if (startTime.equals(savedStartTime) && endTime.isAfter(savedEndTime)) {
                            return false;
                        }

                        if (startTime.isBefore(savedStartTime) && endTime.isAfter(savedEndTime)) {
                            return false;
                        }
                    }
                }
            }
        }
        return true;
    }

    /**
     * Used to check if two dates are the same day.
     * @param startDate The first date to check.
     * @param endDate The second date to check.
     * @return A boolean value indicating whether they are on the same date or not.
     */
    public static Boolean isSameDate(LocalDate startDate, LocalDate endDate) {
        return startDate.equals(endDate);
    }
}
