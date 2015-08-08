package com.unitedvision.sangihe.ehrm.test;

import static org.junit.Assert.*;

import java.sql.Date;
import java.sql.Time;
import java.time.Month;

import org.junit.Test;

import com.unitedvision.sangihe.ehrm.DateUtil;

public class DateUtilTest {

	@Test
	public void test_get_time_from_string() {
		String jamStr = "07:00:00";
		
		Time time = DateUtil.getTime(jamStr, ":");
		Time comparer = DateUtil.getTime(7, 0, 0);
		
		assertEquals(time.getTime(), comparer.getTime());
		assertEquals(7, DateUtil.getHour(time));
		assertEquals(0, DateUtil.getMinute(time));
		assertEquals(0, DateUtil.getSecond(time));
	}
	
	@Test
	public void test_get_date_from_string() {
		String tanggalStr = "01-05-2015";
		
		Date date = DateUtil.getDate(tanggalStr, "-");
		Date comparer = DateUtil.getDate(2015, Month.JANUARY, 5);
		
		assertEquals(date.getTime(), comparer.getTime());
		assertEquals(2015, DateUtil.getYear(date));
		assertEquals(Month.JANUARY, DateUtil.getMonth(date));
		assertEquals(5, DateUtil.getDay(date));
	}
	
	@Test
	public void test_toString() {
		Date date = DateUtil.getDate(2015, Month.JANUARY, 5);

		// format: mm/DD/yyyy
		String fromDate = DateUtil.toStringDate(date, "-");
		String comparer = "1-5-2015";
		
		assertEquals(fromDate, comparer);
	}
	
	@Test
	public void test_toFormattedString() {
		Date date = DateUtil.getDate(2015, Month.JANUARY, 5);

		// format: dd/MM/yyyy
		String fromDate = DateUtil.toFormattedStringDate(date, "-");
		String comparer = "5-1-2015";
		
		assertEquals(fromDate, comparer);
	}
	
	@Test
	public void test() {
		String str = "1/5/2015";
		Date comp = DateUtil.getDate(2015, Month.JANUARY, 5);
		Date dt = DateUtil.getDate(str, "/");
		
		assertEquals(dt, comp);
		
		String str2 = DateUtil.toStringDate(dt, "/");
		assertEquals(str, str2);
	}
	
	@Test
	public void test2() {
		Month month = Month.JANUARY;
		
		assertEquals(month.ordinal(), month.getValue());
	}
	
	@Test
	public void test3() {
		Month month = Month.JANUARY;
		Month month2 = Month.FEBRUARY;
		
		assertTrue(month.compareTo(month2) < 0);
	}
}
