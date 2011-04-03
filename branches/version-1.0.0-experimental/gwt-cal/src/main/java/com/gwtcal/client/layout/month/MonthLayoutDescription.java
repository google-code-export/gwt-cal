package com.gwtcal.client.layout.month;

import com.gwtcal.client.AppointmentProvider;
import com.gwtcal.client.util.DateUtils;

import java.util.ArrayList;
import java.util.Date;

/**
 * Describes the layout for all appointments in all the weeks displayed in a
 * <code>MonthView</code>. This class is responsible for the distribution of the
 * appointments over the multiple weeks they possibly span.
 *
 * @author Carlos D. Morales
 */
public class MonthLayoutDescription<T> {

    private AppointmentProvider<T> appointmentProvider;

    private Date calendarFirstDay = null;

    private Date calendarLastDay = null;

    private WeekLayoutDescription<T>[] weeks = new WeekLayoutDescription[6];

    public MonthLayoutDescription(AppointmentProvider<T> appointmentProvider, Date calendarFirstDay,
                                  int monthViewRequiredRows,
                                  ArrayList<T> appointments, int maxLayer) {
        this.appointmentProvider = appointmentProvider;
        this.calendarFirstDay = calendarFirstDay;
        this.calendarLastDay =
                calculateLastDate(calendarFirstDay, monthViewRequiredRows);
        placeAppointments(appointments, maxLayer);
    }

    public MonthLayoutDescription(AppointmentProvider<T> appointmentProvider, Date calendarFirstDay,
                                  int monthViewRequiredRows,
                                  ArrayList<T> appointments) {
        this(appointmentProvider, calendarFirstDay, monthViewRequiredRows,
                appointments, Integer.MAX_VALUE);
    }

    private void initWeek(int weekIndex, int maxLayer) {
        if (weeks[weekIndex] == null) {
            weeks[weekIndex] = new WeekLayoutDescription<T>(appointmentProvider, calendarFirstDay,
                    calendarLastDay,
                    maxLayer);
        }
    }

    private void placeAppointments(ArrayList<T> appointments,
                                   int maxLayer) {

        for (T appointment : appointments) {
            if (overlapsWithMonth(appointment, calendarFirstDay,
                    calendarLastDay)) {
                int startWeek =
                        calculateWeekFor(appointmentProvider.getStart(appointment), calendarFirstDay);

                /* Place appointments only in this month */
                if (startWeek >= 0 && startWeek < weeks.length) {
                    initWeek(startWeek, maxLayer);
                    if (appointmentProvider.isMultiDay(appointment) || appointmentProvider.isAllDay(appointment)) {
                        positionMultidayAppointment(startWeek, appointment, maxLayer);
                    } else {
                        weeks[startWeek].addAppointment(appointment);
                    }
                }
            }
        }
    }

    private boolean isMultiWeekAppointment(int startWeek, int endWeek) {
        return startWeek != endWeek;
    }

    private void positionMultidayAppointment(int startWeek,
                                             T appointment, int maxLayer) {
        int endWeek = calculateWeekFor(appointmentProvider.getEnd(appointment), calendarFirstDay);

        initWeek(endWeek, maxLayer);
        if (isMultiWeekAppointment(startWeek, endWeek)) {
            distributeOverWeeks(startWeek, endWeek, appointment, maxLayer);
        } else {
            weeks[startWeek].addMultiDayAppointment(appointment);
        }
    }

    private void distributeOverWeeks(int startWeek, int endWeek,
                                     T appointment, int maxLayer) {
        weeks[startWeek].addMultiWeekAppointment(appointment,
                AppointmentWidgetParts.FIRST_WEEK);
        for (int week = startWeek + 1; week < endWeek; week++) {
            initWeek(week, maxLayer);
            weeks[week].addMultiWeekAppointment(appointment,
                    AppointmentWidgetParts.IN_BETWEEN);
        }
        if (startWeek < endWeek) {
            initWeek(endWeek, maxLayer);
            weeks[endWeek].addMultiWeekAppointment(appointment,
                    AppointmentWidgetParts.LAST_WEEK);
        }
    }

    private boolean overlapsWithMonth(T appointment,
                                      Date calendarFirstDate, Date calendarLastDate) {
        return !(appointmentProvider.getStart(appointment).before(calendarFirstDate)
                && appointmentProvider.getEnd(appointment).before(calendarFirstDate)
                || appointmentProvider.getStart(appointment).after(calendarLastDate)
                && appointmentProvider.getEnd(appointment).after(calendarLastDate));
    }

    private int calculateWeekFor(Date testDate, Date calendarFirstDate) {
        //fix for issue 66. differenceInDays returns abs value, causing problems
        if (testDate.before(calendarFirstDate))
            return 0;

        int week = (int) Math.floor(
                DateUtils.differenceInDays(
                        testDate, calendarFirstDate) / 7d);

        return Math.min(week, weeks.length - 1);
    }

    @SuppressWarnings("deprecation")
    private Date calculateLastDate(final Date startDate, int weeks) {
        int daysInMonthGrid = weeks * 7;
        Date endDate = (Date) startDate.clone();
        endDate.setDate(startDate.getDate() + daysInMonthGrid - 1);
        return endDate;
    }

    public WeekLayoutDescription[] getWeekDescriptions() {
        return weeks;
    }

}
