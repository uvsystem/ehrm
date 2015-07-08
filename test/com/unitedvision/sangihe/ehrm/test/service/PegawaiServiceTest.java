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
import com.unitedvision.sangihe.ehrm.duk.repository.PendudukRepository;
import com.unitedvision.sangihe.ehrm.simpeg.Eselon;
import com.unitedvision.sangihe.ehrm.simpeg.Jabatan;
import com.unitedvision.sangihe.ehrm.simpeg.JabatanService;
import com.unitedvision.sangihe.ehrm.simpeg.Pangkat;
import com.unitedvision.sangihe.ehrm.simpeg.Pegawai;
import com.unitedvision.sangihe.ehrm.simpeg.PegawaiService;
import com.unitedvision.sangihe.ehrm.simpeg.UnitKerja;
import com.unitedvision.sangihe.ehrm.simpeg.UnitKerjaService;
import com.unitedvision.sangihe.ehrm.simpeg.UnitKerja.TipeUnitKerja;
import com.unitedvision.sangihe.ehrm.simpeg.repository.PegawaiRepository;
import com.unitedvision.sangihe.ehrm.simpeg.repository.RiwayatJabatanRepository;
import com.unitedvision.sangihe.ehrm.simpeg.repository.RiwayatPangkatRepository;

@RunWith (SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {ApplicationConfig.class})
@Transactional
@TransactionConfiguration (defaultRollback = true)
public class PegawaiServiceTest {

	@Autowired
	private PegawaiService pegawaiService;
	@Autowired
	private UnitKerjaService unitKerjaService;
	@Autowired
	private JabatanService jabatanService;
	@Autowired
	private PendudukRepository pendudukRepository;
	@Autowired
	private PegawaiRepository pegawaiRepository;
	@Autowired
	private RiwayatPangkatRepository riwayatPangkatRepository;
	@Autowired
	private RiwayatJabatanRepository riwayatJabatanRepository;

	private long count;
	private Pegawai pegawai;
	private Jabatan jabatan;
	
	@Before
	public void setup() {
		UnitKerja unitKerja = new UnitKerja();
		unitKerja.setNama("Pengelolaan Data Elektronik");
		unitKerja.setSingkatan("BPDE");
		unitKerja.setTipe(TipeUnitKerja.BAGIAN);
		
		unitKerjaService.simpan(unitKerja);
		
		count = pegawaiRepository.count();
		long countPenduduk = pendudukRepository.count();
		
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
		
		assertEquals(count + 1, pegawaiRepository.count());
		assertEquals(countPenduduk + 1, pendudukRepository.count());
		
		jabatan = new Jabatan();
		jabatan.setEselon(Eselon.V);
		jabatan.setPangkat(Pangkat.IIIC);
		jabatan.setUnitKerja(unitKerja);
		jabatan.setNama("Kepala Sub Bagian Database");
		
		jabatanService.simpan(jabatan);
	}
	
	@Test(expected = PersistenceException.class)
	public void test_simpan_duplicate_pegawai() {
		Pegawai pegawai = new Pegawai();
		pegawai.setNik("7171070512910002");
		pegawai.setNip("090213016");
		pegawai.setNama("Deddy Christoper Kakunsi");
		pegawai.setPassword("dkakunsi");
		pegawai.setTanggalLahir(DateUtil.getDate("12-05-1991"));
		
		Kontak kontak = new Kontak();
		kontak.setEmail("deddy.kakunsi@gmail.com");
		kontak.setTelepon("083247643198");
		pegawai.setKontak(kontak);
		
		pegawaiService.simpan(pegawai);
	}
	
	@Test(expected = PersistenceException.class)
	public void test_simpan_duplicate_nip() {
		Pegawai pegawai = new Pegawai();
		pegawai.setNik("7171070512910001");
		pegawai.setNip("090213016");
		pegawai.setNama("Deddy Christoper Kakunsi");
		pegawai.setPassword("dkakunsi");
		pegawai.setTanggalLahir(DateUtil.getDate("12-05-1991"));
		
		Kontak kontak = new Kontak();
		kontak.setEmail("deddy.kakunsi@gmail.com");
		kontak.setTelepon("083247643198");
		pegawai.setKontak(kontak);
		
		pegawaiService.simpan(pegawai);
	}
	
	@Test
	public void test_mutasi() {
		UnitKerja unitKerja = new UnitKerja();
		unitKerja.setNama("Umum");
		unitKerja.setSingkatan("BUMUM");
		unitKerja.setTipe(TipeUnitKerja.BAGIAN);
		
		unitKerjaService.simpan(unitKerja);
		
		pegawaiService.mutasi(pegawai, unitKerja);
		
		assertEquals(unitKerja, pegawai.getUnitKerja());
	}
	
	@Test
	public void test_promosi_pangkat() {
		pegawaiService.promosi(pegawai, Pangkat.IIIA, DateUtil.getDate("12-01-2013"), null, "001/SK/2015");
		
		assertEquals(Pangkat.IIIA, pegawai.getPangkat());
		
		long countRiwayat = riwayatPangkatRepository.count();
		assertEquals(countRiwayat + 1, riwayatPangkatRepository.count());
	}
	
	@Test
	public void test_promosi_jabatan() {
		pegawaiService.promosi(pegawai, jabatan, DateUtil.getDate("12-01-2014"), null, "002/SK/2015");
		
		assertEquals(jabatan, pegawai.getJabatan());
		
		long countRiwayat = riwayatJabatanRepository.count();
		assertEquals(countRiwayat + 1, riwayatJabatanRepository.count());
	}
}
