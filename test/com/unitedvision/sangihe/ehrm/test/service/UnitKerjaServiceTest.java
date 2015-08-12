package com.unitedvision.sangihe.ehrm.test.service;

import static org.junit.Assert.*;

import java.util.List;

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
import com.unitedvision.sangihe.ehrm.simpeg.UnitKerja;
import com.unitedvision.sangihe.ehrm.simpeg.UnitKerjaService;
import com.unitedvision.sangihe.ehrm.simpeg.UnitKerja.TipeUnitKerja;
import com.unitedvision.sangihe.ehrm.simpeg.repository.UnitKerjaRepository;

@RunWith (SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {ApplicationConfig.class})
@Transactional
@TransactionConfiguration (defaultRollback = true)
public class UnitKerjaServiceTest {

	@Autowired
	private UnitKerjaService unitKerjaService;
	@Autowired
	private UnitKerjaRepository unitKerjaRepository;

	private UnitKerja unitKerja;
	private long countUnitKerja;
	private long countSubUnitKerja;
	
	@Before
	public void setup() {
		countUnitKerja = unitKerjaRepository.count();
		
		unitKerja = new UnitKerja();
		unitKerja.setNama("Unit");
		unitKerja.setSingkatan("UNIT");
		unitKerja.setTipe(TipeUnitKerja.SEKRETARIAT);
		unitKerjaService.simpan(unitKerja);
		assertEquals(countUnitKerja + 1, unitKerjaRepository.count());
		
		UnitKerja subUnitKerja = new UnitKerja(unitKerja);
		subUnitKerja.setNama("Sub Unit");
		subUnitKerja.setSingkatan("SUB");
		subUnitKerja.setTipe(TipeUnitKerja.BAGIAN);
		unitKerjaService.simpan(subUnitKerja);
		assertEquals(countUnitKerja + 2, unitKerjaRepository.count());
	}
	
	@Test(expected = PersistenceException.class)
	public void test_simpan_duplicate() {
		UnitKerja unitKerja = new UnitKerja();
		unitKerja.setNama("Sekretariat Daearah");
		unitKerja.setSingkatan("SETDA");
		unitKerja.setTipe(TipeUnitKerja.SEKRETARIAT);
		
		unitKerjaService.simpan(unitKerja);
		
		assertEquals(countUnitKerja + 1, unitKerjaRepository.count());
	}
	
	@Test
	public void test_update() {
		unitKerja.setSingkatan("KODE");
		unitKerja = unitKerjaService.simpan(unitKerja);
		
		assertEquals("KODE", unitKerja.getSingkatan());
	}
	
	@Test
	public void test_tambah_sub_unit_kerja() {
		UnitKerja subUnitKerja = new UnitKerja();
		subUnitKerja.setNama("Pengelola Data Elektronik");
		subUnitKerja.setSingkatan("BPDE");
		subUnitKerja.setTipe(TipeUnitKerja.BAGIAN);
		subUnitKerja.setIdParent(unitKerja.getId());
		
		unitKerjaService.simpan(subUnitKerja);
		
		assertEquals(countSubUnitKerja + 3, unitKerjaRepository.count());
	}
	
	@Test
	public void test_search() {
		List<UnitKerja> daftarUnitKerja = unitKerjaService.cari("Sek");
		
		assertNotEquals(0, daftarUnitKerja.size());
	}
	
	@Test
	public void test_get() {
		UnitKerja unitKerja2 = unitKerjaService.get("SETDA");
		assertNotNull(unitKerja2);
		assertEquals(unitKerja, unitKerja2);
	}
	
	@Test
	public void test_get_by_id() {
		UnitKerja unitKerja2 = unitKerjaService.get(unitKerja.getId());
		assertNotNull(unitKerja2);
		assertEquals(unitKerja, unitKerja2);
	}
}
