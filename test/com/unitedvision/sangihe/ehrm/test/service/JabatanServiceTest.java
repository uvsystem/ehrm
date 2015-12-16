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
import com.unitedvision.sangihe.ehrm.simpeg.Eselon;
import com.unitedvision.sangihe.ehrm.simpeg.Jabatan;
import com.unitedvision.sangihe.ehrm.simpeg.JabatanService;
import com.unitedvision.sangihe.ehrm.simpeg.Pangkat;
import com.unitedvision.sangihe.ehrm.simpeg.UnitKerja;
import com.unitedvision.sangihe.ehrm.simpeg.UnitKerjaService;
import com.unitedvision.sangihe.ehrm.simpeg.UnitKerja.TipeUnitKerja;
import com.unitedvision.sangihe.ehrm.simpeg.repository.JabatanRepository;

@RunWith (SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {ApplicationConfig.class})
@Transactional
@TransactionConfiguration (defaultRollback = true)
public class JabatanServiceTest {

	@Autowired
	private JabatanService jabatanService;
	@Autowired
	private UnitKerjaService unitKerjaService;
	@Autowired
	private JabatanRepository jabatanRepository;

	private UnitKerja unitKerja;
	
	@Before
	public void setup() {
		unitKerja = new UnitKerja();
		unitKerja.setNama("Pengelolaan Data Elektronik");
		unitKerja.setSingkatan("BPDE");
		unitKerja.setTipe(TipeUnitKerja.BAGIAN);
		
		unitKerjaService.simpan(unitKerja);
		
		long count = jabatanRepository.count();
		
		Jabatan jabatan = new Jabatan();
		jabatan.setEselon(Eselon.Va);
		jabatan.setPangkat(Pangkat.IIIC);
		jabatan.setUnitKerja(unitKerja);
		jabatan.setNama("Kepala Sub Bagian Database");
		
		jabatanService.simpan(jabatan);
		
		assertEquals(count + 1, jabatanRepository.count());
	}
	
	@Test(expected = PersistenceException.class)
	public void test_simpan_duplicate() {
		Jabatan jabatan = new Jabatan();
		jabatan.setEselon(Eselon.Va);
		jabatan.setPangkat(Pangkat.IIIC);
		jabatan.setUnitKerja(unitKerja);
		jabatan.setNama("Kepala Sub Bagian Database");
		
		jabatanService.simpan(jabatan);
	}
}
