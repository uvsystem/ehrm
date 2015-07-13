package com.unitedvision.sangihe.ehrm.test.service;

import static org.junit.Assert.*;

import java.time.Month;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.unitedvision.sangihe.ehrm.ApplicationConfig;
import com.unitedvision.sangihe.ehrm.DateUtil;
import com.unitedvision.sangihe.ehrm.absensi.Kalendar;
import com.unitedvision.sangihe.ehrm.absensi.KalendarService;
import com.unitedvision.sangihe.ehrm.absensi.repository.KalendarRepository;

@RunWith (SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {ApplicationConfig.class})
@Transactional
@TransactionConfiguration (defaultRollback = true)
public class KalendarServiceTest {

	@Autowired
	private KalendarService kalendarService;
	@Autowired
	private KalendarRepository kalendarRepository;

	private long count;
	
	@Before
	public void setup() {
		count = kalendarRepository.count();
		
		Kalendar kalendar = new Kalendar();
		kalendar.setTanggal(DateUtil.getDate(2015, Month.JANUARY, 1));
		
		kalendarService.tambah(kalendar);
		
		assertEquals(count + 1, kalendarRepository.count());
	}
	
	@Test
	public void test_duplicate() {
		Kalendar kalendar = new Kalendar();
		kalendar.setTanggal(DateUtil.getDate(2015, Month.JANUARY, 1));
		
		kalendarService.tambah(kalendar);

		assertEquals(count + 1, kalendarRepository.count());
	}
}
