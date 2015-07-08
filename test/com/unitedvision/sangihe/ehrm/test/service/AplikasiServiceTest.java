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
import com.unitedvision.sangihe.ehrm.duk.Penduduk.Kontak;
import com.unitedvision.sangihe.ehrm.manajemen.Aplikasi;
import com.unitedvision.sangihe.ehrm.manajemen.AplikasiService;
import com.unitedvision.sangihe.ehrm.manajemen.Operator;
import com.unitedvision.sangihe.ehrm.manajemen.Operator.Role;
import com.unitedvision.sangihe.ehrm.manajemen.repository.AplikasiRepository;
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
		unitKerja.setNama("Pengelolaan Data Elektronik");
		unitKerja.setSingkatan("BPDE");
		unitKerja.setTipe(TipeUnitKerja.BAGIAN);
		
		unitKerjaService.simpan(unitKerja);

		pegawai = new Pegawai();
		pegawai.setNik("7171070512910002");
		pegawai.setNip("090213016");
		pegawai.setNama("Deddy Christoper Kakunsi");
		pegawai.setPassword("dkakunsi");
		pegawai.setTanggalLahir(DateUtil.getDate("12-05-1991"));
		pegawai.setUnitKerja(unitKerja);
		
		Kontak kontak = new Kontak();
		kontak.setEmail("deddy.kakunsi@gmail.com");
		kontak.setTelepon("083247643198");
		pegawai.setKontak(kontak);
		
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
	
	public void tambah_operator() {
		Operator operator = aplikasiService.tambahOperator("090213016", "SIMPEG");
		
		assertNotEquals(0, operator.getId());
		assertEquals(Role.OPERATOR, operator.getRole());
	}
	
	public void tambah_admin() {
		Operator operator = aplikasiService.tambahAdmin("090213016", "SIMPEG");
		
		assertNotEquals(0, operator.getId());
		assertEquals(Role.ADMIN, operator.getRole());
	}
}
