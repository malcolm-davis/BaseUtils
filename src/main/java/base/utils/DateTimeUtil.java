package base.utils;

import java.util.Calendar;
import java.util.Date;

/**
 * <p>DateTimeUtil class.</p>
 *
 * @author malcolm
 * @version $Id: $Id
 */
public class DateTimeUtil {
    /**
     * Using Calendar - THE CORRECT WAY *
     *
     * @param startDate a {@link java.util.Date} object.
     * @param endDate a {@link java.util.Date} object.
     * @return a long.
     */
    public static long daysBetween(final Date startDate,
            final Date endDate) {
        final Calendar startDateC = Calendar.getInstance();
        startDateC.setTime(startDate);

        final Calendar endDateC= Calendar.getInstance();
        endDateC.setTime(endDate);

        final Calendar date = (Calendar) startDateC.clone();
        long daysBetween = 0;
        while (date.before(endDateC)) {
            date.add(Calendar.DAY_OF_MONTH, 1);
            daysBetween++;
        }
        return daysBetween;
    }

    /**
     * <p>getCalenderMonth.</p>
     *
     * @param month a {@link java.lang.String} object.
     * @return a int.
     */
    public static int getCalenderMonth(final String month) {
        if( "JANUARY".equalsIgnoreCase(month) ) {
            return 0;
        } else if( "FEBRUARY".equalsIgnoreCase(month) ) {
            return 1;
        } else if( "MARCH".equalsIgnoreCase(month) ) {
            return 2;
        } else if( "APRIL".equalsIgnoreCase(month) ) {
            return 3;
        } else if( "MAY".equalsIgnoreCase(month) ) {
            return 4;
        } else if( "JUNE".equalsIgnoreCase(month) ) {
            return 5;
        } else if( "JULY".equalsIgnoreCase(month) ) {
            return 6;
        } else if( "AUGUST".equalsIgnoreCase(month) ) {
            return 7;
        } else if( "SEPTEMBER".equalsIgnoreCase(month) ) {
            return 8;
        } else if( "OCTOBER".equalsIgnoreCase(month) ) {
            return 9;
        } else if( "NOVEMBER".equalsIgnoreCase(month) ) {
            return 10;
        } else if( "DECEMBER".equalsIgnoreCase(month) ) {
            return 11;
        }

        return 0;
    }

    /**
     * <p>getCalenderMonth.</p>
     *
     * @param month a int.
     * @return a {@link java.lang.String} object.
     */
    public static String getCalenderMonth(final int month) {
        switch(month) {
            case  0: return "January";
            case  1: return "February";
            case  2: return "March";
            case  3: return "April";
            case  4: return "May";
            case  5: return "June";
            case  6: return "July";
            case  7: return "August";
            case  8: return "September";
            case  9: return "October";
            case 10: return "November";
            case 11: return "December";
        }
        return "";
    }

    /**
     * Add num days
     *
     * @param date a {@link java.util.Date} object.
     * @param num a int.
     * @return a {@link java.util.Date} object.
     */
    public static Date addDay(final Date date, final int num) {
        Calendar startCal = getCalendar(date);
        startCal.add(Calendar.DATE, num);
        return startCal.getTime();
    }

    /**
     * Add number months
     *
     * @param date a {@link java.util.Date} object.
     * @param num a int.
     * @return a {@link java.util.Date} object.
     */
    public static Date addMonth(final Date date, final int num) {
        Calendar startCal = getCalendar(date);
        startCal.add(Calendar.MONTH, num);
        return startCal.getTime();
    }

    /**
     * Return difference in number of days
     *
     * @param d1
     * @param d2
     * @return
     */
    @SuppressWarnings("deprecation")
    static long diffDay(final Date d1, final Date d2) {
        return (diffSecond(new Date(-1900, 0, 1), d2) + 3000 * 60 * 60 * 24 * 7)
                / (60 * 60 * 24)
                - (diffSecond(new Date(-1900, 0, 1), d1) + 3000 * 60 * 60 * 24
                        * 7) / (60 * 60 * 24);
    }

    /**
     * Return difference in number of hours
     *
     * @param d1
     * @param d2
     * @return
     */
    @SuppressWarnings("deprecation")
    static long diffHour(final Date d1, final Date d2) {
        return (diffSecond(new Date(-1900, 0, 1), d2) + 3000 * 60 * 60 * 24 * 7)
                / (60 * 60)
                - (diffSecond(new Date(-1900, 0, 1), d1) + 3000 * 60 * 60 * 24
                        * 7) / (60 * 60);
    }

    /**
     * Return difference in number of minutes
     *
     * @param d1
     * @param d2
     * @return
     */
    @SuppressWarnings("deprecation")
    static long diffMinute(final Date d1, final Date d2) {
        return (diffSecond(new Date(-1900, 0, 1), d2) + 3000 * 60 * 60 * 24 * 7)
                / (60)
                - (diffSecond(new Date(-1900, 0, 1), d1) + 3000 * 60 * 60 * 24
                        * 7) / (60);
    }

    /**
     * Return difference in number of months
     *
     * @param d1 a {@link java.util.Date} object.
     * @param d2 a {@link java.util.Date} object.
     * @return a int.
     */
    public static int diffMonth(final Date d1, final Date d2) {
        if (d1 == null || d2 == null) {
            throw new java.lang.IllegalArgumentException("date value is null!");
        }

        int startMonth = year(d1) * 12 + month(d1);
        int endMonth = year(d2) * 12 + month(d2);

        return endMonth - startMonth;
    }

    /**
     * Return difference in number of quarters
     *
     * @param d1
     * @param d2
     * @return
     */
    static int diffQuarter(final Date d1, final Date d2) {
        if (d1 == null || d2 == null) {
            throw new java.lang.IllegalArgumentException("date value is null!");
        }

        int startQuter = year(d1) * 4 + quarter(d1);
        int endQuter = year(d2) * 4 + quarter(d2);

        return endQuter - startQuter;
    }

    /**
     * Return difference in number of seconds
     *
     * @param d1
     * @param d2
     * @return
     */
    static long diffSecond(final Date d1, final Date d2) {
        if (d1 == null || d2 == null) {
            throw new java.lang.IllegalArgumentException("date value is null!");
        }
        long diff = d2.getTime() - d1.getTime();

        return (new Long(diff / 1000)).longValue();
    }

    /**
     * Return difference in number of weeks
     *
     * @param d1
     * @param d2
     * @return
     */
    @SuppressWarnings("deprecation")
    static long diffWeek(final Date d1, final Date d2) {
        Date baseDay = new Date(-1900, 0, 7);

        int diffDay = 1 - Integer.valueOf(weekDay(baseDay)).intValue();

        baseDay = addDay(baseDay, diffDay);

        return (diffSecond(baseDay, d2) + 3000 * 60 * 60 * 24 * 7)
                / (60 * 60 * 24 * 7)
                - (diffSecond(baseDay, d1) + 3000 * 60 * 60 * 24 * 7)
                / (60 * 60 * 24 * 7);
    }

    /**
     * Return difference in number of years
     *
     * @param d1
     * @param d2
     * @return
     */
    static int diffYear(final Date d1, final Date d2) {
        if (d1 == null || d2 == null) {
            throw new java.lang.IllegalArgumentException("date value is null!");
        }
        int startYear = year(d1);
        int endYear = year(d2);

        return endYear - startYear;
    }

    /**
     * <p>getCalendar.</p>
     *
     * @param d a {@link java.util.Date} object.
     * @return a {@link java.util.Calendar} object.
     */
    public static Calendar getCalendar(final Date d) {
        if (d == null) {
            throw new java.lang.IllegalArgumentException("date value is null!");
        }
        Calendar c = Calendar.getInstance();
        c.setTime(d);
        return c;
    }

    /**
     * Month of date/time value d. Return month number (1-12)
     *
     * @param d a {@link java.util.Date} object.
     * @return a int.
     */
    public static int month(final Date d) {
        if (d == null) {
            throw new java.lang.IllegalArgumentException("date value is null!");
        }

        return getCalendar(d).get(Calendar.MONTH) + 1;
    }

    /**
     * Quarter number (1 to 4) of date/time value date
     *
     * @return quarter
     * @param d a {@link java.util.Date} object.
     */
    public static int quarter(final Date d) {
        if (d == null) {
            throw new java.lang.IllegalArgumentException("date value is null!");
        }

        int month = getCalendar(d).get(Calendar.MONTH);
        switch (month) {
            case Calendar.JANUARY:
            case Calendar.FEBRUARY:
            case Calendar.MARCH:
                return 1;
            case Calendar.APRIL:
            case Calendar.MAY:
            case Calendar.JUNE:
                return 2;
            case Calendar.JULY:
            case Calendar.AUGUST:
            case Calendar.SEPTEMBER:
                return 3;
            case Calendar.OCTOBER:
            case Calendar.NOVEMBER:
            case Calendar.DECEMBER:
                return 4;
            default:
                return -1;
        }
    }

    /**
     * Quarter start of date/time value date.
     *
     * Month: (JANUARY, APRIL, JULY, OCTOBER)
     * Day: 01
     *
     * @return Calendar start of the quarter
     * @param d a {@link java.util.Date} object.
     */
    public static Calendar getCalendarQuarterStart(final Date d) {
        if (d == null) {
            throw new java.lang.IllegalArgumentException("date value is null!");
        }

        Calendar c = getCalendar(d);

        // adjust Calendar based on month
        int month = c.get(Calendar.MONTH);
        switch (month) {
            case Calendar.JANUARY:
            case Calendar.FEBRUARY:
            case Calendar.MARCH:
                c.set(Calendar.MONTH, Calendar.JANUARY);
                break;
            case Calendar.APRIL:
            case Calendar.MAY:
            case Calendar.JUNE:
                c.set(Calendar.MONTH, Calendar.APRIL);
                break;
            case Calendar.JULY:
            case Calendar.AUGUST:
            case Calendar.SEPTEMBER:
                c.set(Calendar.MONTH, Calendar.JULY);
                break;
            case Calendar.OCTOBER:
            case Calendar.NOVEMBER:
            case Calendar.DECEMBER:
                c.set(Calendar.MONTH, Calendar.OCTOBER);
                break;
            default:
                throw new java.lang.IllegalArgumentException(
                    "Something very wrong!  month=" + month);
        }

        c.set(Calendar.DAY_OF_MONTH, 1);
        return c;
    }

    /**
     * Quarter start of date/time value date.
     *
     * Month: (MARCH, JUNE, SEPTEMBER, DECEMBER)
     * Day: (depends on length of month)
     *
     * @return Calendar start of the quarter
     * @param d a {@link java.util.Date} object.
     */
    public static Calendar getCalendarQuarterEnd(final Date d) {
        if (d == null) {
            throw new java.lang.IllegalArgumentException("date value is null!");
        }

        Calendar c = getCalendar(d);

        // adjust Calendar based on month
        int month = c.get(Calendar.MONTH);
        switch (month) {
            case Calendar.JANUARY:
            case Calendar.FEBRUARY:
            case Calendar.MARCH:
                c.set(Calendar.MONTH, Calendar.MARCH);
                break;
            case Calendar.APRIL:
            case Calendar.MAY:
            case Calendar.JUNE:
                c.set(Calendar.MONTH, Calendar.JUNE);
                break;
            case Calendar.JULY:
            case Calendar.AUGUST:
            case Calendar.SEPTEMBER:
                c.set(Calendar.MONTH, Calendar.SEPTEMBER);
                break;
            case Calendar.OCTOBER:
            case Calendar.NOVEMBER:
            case Calendar.DECEMBER:
                c.set(Calendar.MONTH, Calendar.DECEMBER);
                break;
            default:
                throw new java.lang.IllegalArgumentException(
                    "Something very wrong!  month=" + month);
        }

        int monthLength = c.getActualMaximum(Calendar.DAY_OF_MONTH);
        c.set(Calendar.DAY_OF_MONTH, monthLength);
        return c;
    }

    /**
     * Day the week. Return a number 1 (Sunday) to 7 (Saturday).
     *
     * @param d a {@link java.util.Date} object.
     * @return a {@link java.lang.String} object.
     */
    public static String weekDay(final Date d) {
        if (d == null) {
            throw new java.lang.IllegalArgumentException("date value is null!");
        }

        return String.valueOf(getCalendar(d).get(Calendar.DAY_OF_WEEK));
    }

    /**
     * 4-digit year number of date/time value d
     *
     * @param d a {@link java.util.Date} object.
     * @return a int.
     */
    public static int year(final Date d) {
        if (d == null) {
            throw new java.lang.IllegalArgumentException("date value is null!");
        }

        return getCalendar(d).get(Calendar.YEAR);
    }

}
