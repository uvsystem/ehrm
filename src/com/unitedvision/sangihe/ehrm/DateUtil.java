package com.unitedvision.sangihe.ehrm;

import java.sql.Time;
import java.sql.Date;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Month;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Calendar;

/**
 * Utility class to working with java.util.Date and java.util.Calendar.
 * 
 * @author Deddy Christoper Kakunsi
 *
 */
public class DateUtil {
	public static final int EPOCH_YEAR = 1970;
	public static final int EPOCH_MONTH = 1;
	public static final int EPOCH_DAY = 1;
	
	public static final long DAY_IN_MILIS = 86400000L;
	
	public final static String DEFAULT_DELIMETER = "-";
	
	public static long toMilis(int year, int month, int day) {
		LocalDate epoch = LocalDate.of(EPOCH_YEAR, EPOCH_MONTH, EPOCH_DAY);
		
		int lastDay = getLastDay(month, year);
		
		if (day > lastDay) {
			month += 1;
			day -= lastDay;
		}
		
		LocalDate created = LocalDate.of(year, month, day);

		long p = ChronoUnit.DAYS.between(epoch, created);

		return p * DAY_IN_MILIS;
	}
	
	public static Time getTime() {
		LocalTime localTime = getNowLocalTime();
		
		return toTime(localTime);
	}
	
	public static Time getTime(int hour, int minute, int second) {
		LocalTime localTime = getLocalTime(hour, minute, second);
		
		return toTime(localTime);
	}

	public static Time getTime(String timeString) {
		if (timeString == null || timeString.equals(""))
			return null;
		
		LocalTime localTime = getLocalTime(timeString);
		
		return toTime(localTime);
	}

	public static Time getTime(String timeString, String delim) {
		if (timeString == null || timeString.equals(""))
			return null;
		
		LocalTime localTime = getLocalTime(timeString, delim);
		
		return toTime(localTime);
	}

	public static Date getNow() {
		LocalDate localDate = getNowLocalDate();
		
		return toDate(localDate);
	}
	
	public static Date getDate(int year, int month, int day) {
		LocalDate localDate = getLocalDate(day, month, year);
		
		return toDate(localDate);
	}

	/**
	 * Format mm/DD/YYYY
	 * @param dateString
	 * @return
	 */
	public static Date getDate(String dateString) {
		if (dateString == null || dateString.equals(""))
			return null;
		
		LocalDate localDate = getLocalDate(dateString);
		
		return toDate(localDate);
	}

	public static Date getDate(String dateString, String delim) {
		if (dateString == null || dateString.equals(""))
			return null;
		
		LocalDate localDate = getLocalDate(dateString, delim);
		
		return toDate(localDate);
	}

	/**
	 * Create date object.
	 * @param year {@code int}
	 * @param month {@code Month}
	 * @param date {@code int}
	 * @return
	 */
	public static Date getDate(int year, Month month, int day) {
		LocalDate localDate = getLocalDate(day, month, year);
		
		return toDate(localDate);
	}

	public static int[] createArray(Date date) {
		int[] arrOfDate = new int[3];

		arrOfDate[0] = getDay(date);
		arrOfDate[1] = getMonthInt(date);
		arrOfDate[2] = getYear(date);
		
		return arrOfDate;
	}
	
	public static int[] createArray(Time time) {
		int[] arrOfTime = new int[3];
		
		arrOfTime[0] = getHour(time);
		arrOfTime[1] = getMinute(time);
		arrOfTime[2] = getSecond(time);
		
		return arrOfTime;
	}

	public static Date getFirstDate() {
		LocalDate localDate = LocalDate.now();
		Date date = Date.valueOf(localDate);
		
		int year = getYear(date);
		Month month = getMonth(date);
		
		LocalDate firstDate = LocalDate.of(year, month, 1);
		
		return toDate(firstDate);
	}

	public static Date getLastDate() {
		LocalDate localDate = LocalDate.now();
		Date date = Date.valueOf(localDate);
		
		int year = getYear(date);
		Month month = getMonth(date);
		
		LocalDate firstDate = LocalDate.of(year, month, getLastDay(month, year));
		
		return toDate(firstDate);
	}

	public static int getMonthInt(Calendar calendar) {
		return calendar.get(Calendar.MONTH) + 1;
	}
	
	public static int getLastDay(Month month, int year) {
		int lastDate = month.maxLength();

		if ((month.equals(Month.FEBRUARY)) && (year % 4 != 0))
			lastDate = 28;
		
		return lastDate;
	}

	public static int getLastDay(Calendar cal) {
		Month month = getMonth(cal);
		int year = getYear(cal);

		return getLastDay(month, year);
	}

	/**
	 * Return the last day of month and year.
	 * @param month {@code int}
	 * @param year {@code int}
	 * @return the last day
	 */
	public static int getLastDay(int month, int year) {
		return getLastDay(Month.of(month), year);
	}
	
	/**
	 * Return month in {@code int} representation from {@code String}.
	 * @param month
	 * @return month in {@code int} representation
	 */
	public static int getMonthInt(String month) {
		return Integer.parseInt(month);
	}

	public static Month getMonth(Date date) {
		LocalDate localDate = date.toLocalDate();
		
		return localDate.getMonth();
	}
	
	public static int getMonthInt(Date date) {
		Month month = getMonth(date);
		
		return month.getValue();
	}
	
	/**
	 * Return month in {@code Month} representation from {@code Calendar}.
	 * @param calendar
	 * @return month in {@code Month} representation
	 */
	public static Month getMonth(Calendar calendar) {
		return Month.of(getMonthInt(calendar));
	}

	public static int getYear(Calendar calendar) {
		return calendar.get(Calendar.YEAR);
	}

	public static int getYear(Date date) {
		LocalDate localDate = date.toLocalDate();
		
		return localDate.getYear();
	}

	public static int getDay(Date date) {
		LocalDate localDate = date.toLocalDate();
		
		return localDate.getDayOfMonth();
	}
	
	public static int getHour(Time time) {
		LocalTime localTime = time.toLocalTime();
		
		return localTime.getHour();
	}
	
	public static int getMinute(Time time) {
		LocalTime localTime = time.toLocalTime();
		
		return localTime.getMinute();
	}
	
	public static int getSecond(Time time) {
		LocalTime localTime = time.toLocalTime();
		
		return localTime.getSecond();
	}
	
	/**
	 * Check whether two dates equals or not. Comparison between year, month, and day only.
	 * @param date1
	 * @param date2
	 * @return true if year, month, and day are equals. Otherwise, false.
	 */
	public static boolean equals(Date date1, Date date2) {
		if (getYear(date1) != getYear(date2))
			return false;
		if (getMonthInt(date1) != getMonthInt(date2))
			return false;
		if (getDay(date1) != getDay(date2))
			return false;
		return true;
	}

	public static Date add(Date awal, int i) {
		LocalDate localDate = awal.toLocalDate();
		
		LocalDate newDate = localDate.plusDays(i);

		return toDate(newDate);
	}

	public static Date substract(Date awal, int i) {
		LocalDate localDate = awal.toLocalDate();
		
		LocalDate newDate = localDate.minusDays(i);

		return toDate(newDate);
	}
	
	// LocalDate
	private static final ZoneId DEFAULT_ZONE_ID = ZoneId.of("Asia/Singapore");
	
	public static LocalDate getLocalDate(ZoneId zoneId) {
		return LocalDate.now(zoneId);
	}
	
	/**
	 * Format mm/DD/YYYY
	 * @param dateString
	 * @return
	 */
	public static LocalDate getLocalDate(String dateString) {
		return getLocalDate(dateString, DEFAULT_DELIMETER);
	}
	
	/**
	 * Format mm/DD/YYYY
	 * @param dateString
	 * @param delim
	 * @return
	 */
	public static LocalDate getLocalDate(String dateString, String delim) {
		if (dateString == null || dateString.equals(""))
			return null;
		
		// month-day-year
		String elStr[] = dateString.split(delim);

		return getLocalDate(Integer.parseInt(elStr[1]), getMonthInt(elStr[0]), Integer.parseInt(elStr[2]));
	}
	
	public static LocalDate getLocalDate(int day, int month, int year) {
		int lastDay = getLastDay(month, year);
		int antara = day - lastDay;
		
		if (antara > 0) {
			month++;
			day = antara;
		}
		
		return LocalDate.of(year, month, day);
	}
	
	public static LocalDate getLocalDate(int day, Month month, int year) {
		int lastDay = getLastDay(month, year);
		int antara = day - lastDay;
		
		if (antara > 0) {
			int monthValue = month.getValue();
			month = Month.of(++monthValue);
			day = antara;
		}

		return LocalDate.of(year, month, day);
	}

	public static LocalDate getNowLocalDate() {
		return getLocalDate(DEFAULT_ZONE_ID);
	}
	
	public static Instant getInstant(LocalDate localDate) {
		return localDate.atStartOfDay().atZone(DEFAULT_ZONE_ID).toInstant();
	}
	
	public static Date toDate(LocalDate localDate) {
		return Date.valueOf(localDate);
	}
	
	// LocalTime
	public static LocalTime getLocalTime(ZoneId zoneId) {
		return LocalTime.now(DEFAULT_ZONE_ID);
	}

	/**
	 * The string must be "13:10:05".
	 * @param timeString
	 * @return
	 */
	public static LocalTime getLocalTime(String timeString) {
		return getLocalTime(timeString, ":");
		//return LocalTime.parse(timeString);
	}
	
	public static LocalTime getLocalTime(String timeString, String delim) {
		if (timeString == null || timeString.equals(""))
			return null;
		
		String elStr[] = timeString.split(delim);
		
		int second = 0;
		if (elStr.length == 3)
			second = Integer.parseInt(elStr[2]);
		
		return getLocalTime(Integer.parseInt(elStr[0]), getMonthInt(elStr[1]), second);
	}
	
	public static LocalTime getLocalTime(int hour, int minute, int second) {
		return LocalTime.of(hour, minute, second);
	}

	public static LocalTime getNowLocalTime() {
		return getLocalTime(DEFAULT_ZONE_ID);
	}
	
	public static Time toTime(LocalTime localTime) {
		return Time.valueOf(localTime);
	}

	public static boolean isFormatted(String dateString) {
		// bulan-tanggal-tahun
		String arrStr[] = dateString.split(DEFAULT_DELIMETER);
		
		if ( arrStr[0].length() < 4)
			return false;
		return true;
	}
	
	public static String formatDateString(String dateString) {
		// bulan-tanggal-tahun
		String arrStr[] = dateString.split(DEFAULT_DELIMETER);

		if (arrStr[1].length() == 1)
			arrStr[1] = String.format("0%s", arrStr[1]);
		
		if (arrStr[0].length() == 1)
			arrStr[0] = String.format("0%s", arrStr[0]);
		
		// tahun-bulan-tanggal
		return String.format("%s-%s-%s", arrStr[2], arrStr[0], arrStr[1]);
	}
	
	public static String toFormattedStringDate(Date date, String delim) {
		int[] arrOfDate = createArray(date);
		
		return String.format("%d%s%d%s%d", arrOfDate[0], delim, arrOfDate[1], delim, arrOfDate[2]);
	}

	public static String toFormattedStringTime(Time time, String delim) {
		int[] arrOfDate = createArray(time);
		String[] arr = new String[3];

		int index = -1;
		for (int i : arrOfDate) {
			
			index++;
			arr[index] = String.valueOf(i);
			if ( String.valueOf(i).length() < 2)
				arr[index] = String.format("0%d", i);
			
		}
		
		return String.format("%s%s%s%s%s", arr[0], delim, arr[1], delim, arr[2]);
	}
	
	/**
	 * Code the date.
	 * @param date
	 * @return
	 */
	public static String codedDate(Date date) {
		return String.format("%d%d%d", getYear(date), getMonthInt(date), getDay(date));
	}
	
	/**
	 * Code the time.
	 * @param date
	 * @return
	 */
	public static String codedTime(Time time) {
		return String.format("%d%d%d", getHour(time), getMinute(time), getSecond(time));
	}
	
	/**
	 * Code it.
	 * @param date
	 * @return
	 */
	public static String codedString(Date date) {
		Time time = new Time(date.getTime());
		
		return String.format("%s%s", codedDate(date), codedTime(time));
	}
}
