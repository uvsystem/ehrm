package com.unitedvision.sangihe.ehrm.test.service;

import static org.junit.Assert.*;

import javax.persistence.PersistenceException;

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
	
	@Before
	public void setup() {
		long count = kalendarRepository.count();
		
		Kalendar kalendar = new Kalendar();
		kalendar.setTanggal(DateUtil.getNow());
		
		kalendarService.simpan(kalendar);
		
		assertEquals(count + 1, kalendarRepository.count());
	}
	
	@Test(expected = PersistenceException.class)
	public void test_duplicate() {
		Kalendar kalendar = new Kalendar();
		kalendar.setTanggal(DateUtil.getNow());
		
		kalendarService.simpan(kalendar);
	}
}
