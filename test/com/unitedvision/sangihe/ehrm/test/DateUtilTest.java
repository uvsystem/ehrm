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
		String tanggalStr = "01-02-2015";
		
		Date date = DateUtil.getDate(tanggalStr, "-");
		Date comparer = DateUtil.getDate(2015, Month.JANUARY, 2);
		
		assertEquals(date.getTime(), comparer.getTime());
		assertEquals(2015, DateUtil.getYear(date));
		assertEquals(Month.JANUARY, DateUtil.getMonth(date));
		assertEquals(2, DateUtil.getDay(date));
	}
}
