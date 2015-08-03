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
import com.unitedvision.sangihe.ehrm.manajemen.Aplikasi;
import com.unitedvision.sangihe.ehrm.manajemen.AplikasiService;
import com.unitedvision.sangihe.ehrm.manajemen.Operator;
import com.unitedvision.sangihe.ehrm.manajemen.Operator.Role;
import com.unitedvision.sangihe.ehrm.manajemen.repository.AplikasiRepository;
import com.unitedvision.sangihe.ehrm.manajemen.repository.OperatorRepository;
import com.unitedvision.sangihe.ehrm.simpeg.Pegawai;
import com.unitedvision.sangihe.ehrm.simpeg.PegawaiService;
import com.unitedvision.sangihe.ehrm.simpeg.UnitKerja;
import com.unitedvision.sangihe.ehrm.simpeg.UnitKerja.TipeUnitKerja;
import com.unitedvision.sangihe.ehrm.simpeg.UnitKerjaService;

@RunWith (SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {ApplicationConfig.class})
@Transactional
@TransactionConfiguration (defaultRollback = true)
public class AplikasiServiceTest {

	@Autowired
	private AplikasiService aplikasiService;
	@Autowired
	private UnitKerjaService unitKerjaService;
	@Autowired
	private PegawaiService pegawaiService;
	@Autowired
	private AplikasiRepository aplikasiRepository;
	@Autowired
	private OperatorRepository operatorRepository;

	private Pegawai pegawai;
	
	@Before
	public void setup() {
		long count = aplikasiRepository.count();
		
		Aplikasi aplikasi = new Aplikasi();
		aplikasi.setKode("SIMPEG");
		aplikasi.setNama("Sistem Informasi Manajemen Pegawai");
		aplikasi.setUrl("https://sistem.sangihekab.go.id/simpeg");
		
		aplikasiService.simpan(aplikasi);
		
		assertEquals(count + 1, aplikasiRepository.count());
		
		UnitKerja unitKerja = new UnitKerja();
		unitKerja.setNama("Unit Kerja");
		unitKerja.setSingkatan("uk");
		unitKerja.setTipe(TipeUnitKerja.BAGIAN);
		
		unitKerjaService.simpan(unitKerja);

		pegawai = new Pegawai();
		pegawai.setNik("7171070512910000");
		pegawai.setNip("090213010");
		pegawai.setNama("Deddy Christoper Kakunsi");
		pegawai.setPassword("dkakunsi");
		pegawai.setTanggalLahir(DateUtil.getDate("12-05-1991"));
		pegawai.setUnitKerja(unitKerja);
		pegawai.setEmail("deddy.kakunsi@gmail.com");
		pegawai.setTelepon("083247643198");
		
		pegawaiService.simpan(pegawai);
	}

	@Test(expected = PersistenceException.class)
	public void simpan_duplicate() {
		Aplikasi aplikasi = new Aplikasi();
		aplikasi.setKode("SIMPEG");
		aplikasi.setNama("Sistem Informasi Manajemen Pegawai");
		aplikasi.setUrl("https://sistem.sangihekab.go.id/simpeg");
		
		aplikasiService.simpan(aplikasi);
	}
	
	@Test
	public void tambah_operator() {
		long count = operatorRepository.count();
		
		Aplikasi aplikasi = aplikasiService.tambahOperator("090213010", "SIMPEG");
		
		for (Operator operator : aplikasi.getDaftarOperator()) {
			if (operator.getPegawai().getNip().equals("090213010"))
				assertEquals(Role.OPERATOR, operator.getRole());
		}
		
		assertEquals(count + 1, operatorRepository.count());
	}
	
	@Test
	public void tambah_admin() {
		long count = operatorRepository.count();
		
		Aplikasi aplikasi = aplikasiService.tambahAdmin("090213010", "SIMPEG");
		
		for (Operator operator : aplikasi.getDaftarOperator()) {
			if (operator.getPegawai().getNip().equals("090213010"))
				assertEquals(Role.ADMIN, operator.getRole());
		}
		
		assertEquals(count + 1, operatorRepository.count());
	}
	
	@Test
	public void test_get() {
		aplikasiService.tambahAdmin("090213010", "SIMPEG");
		
		Aplikasi aplikasi = aplikasiService.getByKode("SIMPEG");
		
		assertNotNull(aplikasi);
		assertNotEquals(0, aplikasi.getDaftarOperator().size());
	}
	
	@Test
	public void test_hapus() {
		long count = operatorRepository.count();
		
		Aplikasi aplikasi = aplikasiService.tambahAdmin("090213010", "SIMPEG");
		assertEquals(count + 1, operatorRepository.count());
		assertEquals(1, aplikasi.getDaftarOperator().size());

		System.out.println("HERE");
		aplikasiService.hapusOperator(aplikasi.getDaftarOperator().get(0).getId());
		aplikasi = aplikasiService.getByKode("SIMPEG");
		
		assertEquals(count, operatorRepository.count());
		assertEquals(0, aplikasi.getDaftarOperator().size());
	}
}
