package base.utils;

import java.util.Calendar;
import java.util.Date;
import java.util.logging.Logger;

/**
 * Provides a set of functions for working with the difference between two
 * dates.
 *
 * @author malcolm
 * @version $Id: $Id
 */
public class DateTimeSpan {

    /**
     * <p>years.</p>
     *
     * @return the number of years between two dates
     * @param p_startDate a {@link java.util.Date} object.
     * @param p_endDate a {@link java.util.Date} object.
     */
    public static int years(final Date p_startDate, final Date p_endDate) {
        if (p_startDate == null || p_endDate == null) {
            return 0;
        }
        Calendar startCal = Calendar.getInstance();
        startCal.setTime(p_startDate);
        // Get the year of startDate
        int startYear = startCal.get(Calendar.YEAR) - 1900;
        Calendar endCal = Calendar.getInstance();
        endCal.setTime(p_endDate);
        // Get the year of endDate
        int endYear = endCal.get(Calendar.YEAR) - 1900;
        assert (endYear >= startYear);
        // Get the span of the endYear and the startYear, only thinking of the
        // year but month and day
        int spanYear = endYear - startYear;
        // startCal.set( Calendar.YEAR, endYear + 1900 );
        startCal.add(Calendar.YEAR, spanYear);

        Date startDate = startCal.getTime();
        Date endDate = endCal.getTime();

        /*
         * the value 0 if the argument is a Date equal to this Date; a value
         * less than 0 if the argument is a Date after this Date; and a value
         * greater than 0 if the argument is a Date before this Date.
         */
        if (startDate.compareTo(endDate) > 0) {
            spanYear -= 1;
        }
        return spanYear;
    }

    /**
     * <p>months.</p>
     *
     * @param startDate A date object that represents the start of the span
     * @param endDate A date object that represents the end of the span
     * @return the number of months between two dates
     */
    public static int months(final Date startDate, final Date endDate) {
        if (startDate == null || endDate == null) {
            return 0;
        }
        Calendar startCal = Calendar.getInstance();
        startCal.setTime(startDate);

        Calendar endCal = Calendar.getInstance();
        endCal.setTime(endDate);

        int startMonth = startCal.get(Calendar.YEAR) * 12
                + startCal.get(Calendar.MONTH);
        int endMonth = endCal.get(Calendar.YEAR) * 12
                + endCal.get(Calendar.MONTH);

        int spanMonth = endMonth - startMonth;

        startCal.add(Calendar.MONTH, spanMonth);

        if (startCal.getTime().compareTo(endCal.getTime()) > 0) {
            spanMonth--;
        }

        return spanMonth;
    }

    /**
     * <p>days.</p>
     *
     * @param startDate A date object that represents the start of the span
     * @param endDate A date object that represents the end of the span
     * @return the number of days between two dates
     */
    public static int days(final Date startDate, final Date endDate) {
        if (startDate == null || endDate == null) {
            return 0;
        }
        long diff = endDate.getTime() - startDate.getTime();

        return (new Long(diff / (1000 * 60 * 60 * 24))).intValue();

    }

    /**
     * <p>hours.</p>
     *
     * @param startDate A date object that represents the start of the span
     * @param endDate A date object that represents the end of the span
     * @return the number of hours between two dates
     */
    public static int hours(final Date startDate, final Date endDate) {
        if (startDate == null || endDate == null) {
            return 0;
        }

        long diff = endDate.getTime() - startDate.getTime();

        return (new Long(diff / (1000 * 60 * 60))).intValue();
    }

    /**
     * <p>minutes.</p>
     *
     * @param startDate A date object that represents the start of the span
     * @param endDate A date object that represents the end of the span
     * @return the number of minutes between two dates
     */
    public static int minutes(final Date startDate, final Date endDate) {
        if (startDate == null || endDate == null) {
            return 0;
        }

        long diff = endDate.getTime() - startDate.getTime();

        return (new Long(diff / (1000 * 60))).intValue();
    }

    /**
     * <p>seconds.</p>
     *
     * @param startDate A date object that represents the start of the span
     * @param endDate A date object that represents the end of the span
     * @return the number of seconds between two dates
     */
    public static int seconds(final Date startDate, final Date endDate) {
        if (startDate == null || endDate == null) {
            return 0;
        }
        long diff = endDate.getTime() - startDate.getTime();

        return (new Long(diff / 1000)).intValue();

    }

    /**
     * <p>addDate.</p>
     *
     * @param startDate A date object that represent the bass date.
     * @param years The number of years to add to the date.
     * @param months The number of month to add to the date.
     * @param days The number of days to add to the date.
     * @return A date that results from adding the years, months, and days to
     *         the start date.
     */
    public static Date addDate(final Date startDate, final int years, final int months, final int days) {
        Calendar startCal = Calendar.getInstance();
        Date firstDate = startDate;
        startCal.setTime(firstDate);
        /*
         * Add years first. Then, using the resulting date, add the months.
         * Then, using the resulting date, add the days.
         */
        startCal.add(Calendar.YEAR, years);
        startCal.add(Calendar.MONTH, months);
        startCal.add(Calendar.DATE, days);

        return startCal.getTime();
    }

    /**
     * <p>addTime.</p>
     *
     * @param startDate A date object that represents the base date
     * @param hours The number of hours to add to the date
     * @param minutes The number of minutes to add to the date
     * @param seconds The number of seconds to add to the date
     * @return A date that results adding the hours, minutes, seconds to the
     *         start date.
     */
    public static Date addTime(final Date startDate, final int hours, final int minutes,
            final int seconds) {
        Calendar startCal = Calendar.getInstance();
        Date firstDate = startDate;
        startCal.setTime(firstDate);
        /*
         * Add years first. Then, using the resulting date, add the months.
         * Then, using the resulting date, add the days.
         */
        startCal.add(Calendar.HOUR_OF_DAY, hours);
        startCal.add(Calendar.MINUTE, minutes);
        startCal.add(Calendar.SECOND, seconds);

        return startCal.getTime();
    }

    /**
     * <p>subDate.</p>
     *
     * @param startDate A date object that represent the bass date.
     * @param years The number of years to add to the date.
     * @param months The number of month to add to the date.
     * @param days The number of days to add to the date.
     * @return A date that results from subtract the years, months, and days
     *         from the start date.
     */
    public static Date subDate(final Date startDate, final int years, final int months, final int days) {
        Calendar startCal = Calendar.getInstance();
        Date firstDate = startDate;
        startCal.setTime(firstDate);
        return addDate(startDate, -years, -months, -days);
    }

    /**
     * <p>subTime.</p>
     *
     * @param startDate A date object that represents the base date
     * @param hours The number of hours to add to the date
     * @param minutes The number of minutes to add to the date
     * @param seconds The number of seconds to add to the date
     * @return A date that results subtracting the hours, minutes, seconds from
     *         the start date.
     */
    public static Date subTime(final Date startDate, final int hours, final int minutes,
            final int seconds) {
        return addTime(startDate, -hours, -minutes, -seconds);
    }

    /**
     * logger used to log syntax errors.
     */
    static protected Logger logger = Logger.getLogger(
        DateTimeSpan.class.getName());

    /**
     * The class is static, the application cannot create an instance of this
     * class
     */
    private DateTimeSpan() {
        //
    }


}
