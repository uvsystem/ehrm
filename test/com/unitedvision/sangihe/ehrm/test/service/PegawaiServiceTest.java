package com.unitedvision.sangihe.ehrm.test.service;

import static org.junit.Assert.*;

import java.time.Month;
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
import com.unitedvision.sangihe.ehrm.DateUtil;
import com.unitedvision.sangihe.ehrm.IdenticRelationshipException;
import com.unitedvision.sangihe.ehrm.NullCollectionException;
import com.unitedvision.sangihe.ehrm.duk.repository.PendudukRepository;
import com.unitedvision.sangihe.ehrm.simpeg.Eselon;
import com.unitedvision.sangihe.ehrm.simpeg.Jabatan;
import com.unitedvision.sangihe.ehrm.simpeg.JabatanService;
import com.unitedvision.sangihe.ehrm.simpeg.NoJabatanException;
import com.unitedvision.sangihe.ehrm.simpeg.NoPangkatException;
import com.unitedvision.sangihe.ehrm.simpeg.Pangkat;
import com.unitedvision.sangihe.ehrm.simpeg.Pegawai;
import com.unitedvision.sangihe.ehrm.simpeg.PegawaiService;
import com.unitedvision.sangihe.ehrm.simpeg.UnitKerja;
import com.unitedvision.sangihe.ehrm.simpeg.UnitKerjaService;
import com.unitedvision.sangihe.ehrm.simpeg.UnitKerja.TipeUnitKerja;
import com.unitedvision.sangihe.ehrm.simpeg.repository.JabatanRepository;
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
	@Autowired
	private JabatanRepository jabatanRepository;

	private long countPegawai;
	private long countPenduduk;
	private long countRiwayatPangkat;
	private long countRiwayatJabatan;
	private Pegawai pegawai;
	private Jabatan jabatanKaSubBagDatabase;
	private UnitKerja unitKerja;
	
	@Before
	public void setup() throws IdenticRelationshipException, NullCollectionException {
		unitKerja = new UnitKerja();
		unitKerja.setNama("Pengelolaan Data Elektronik");
		unitKerja.setSingkatan("BPDE");
		unitKerja.setTipe(TipeUnitKerja.BAGIAN);
		unitKerjaService.simpan(unitKerja);
		
		countPegawai = pegawaiRepository.count();
		countPenduduk = pendudukRepository.count();
		
		pegawai = new Pegawai();
		pegawai.setNik("7171070512910002");
		pegawai.setNip("090213016");
		pegawai.setNama("Deddy Christoper Kakunsi");
		pegawai.setPassword("dkakunsi");
		pegawai.setTanggalLahir(DateUtil.getDate("12-05-1991"));
		pegawai.setUnitKerja(unitKerja);
		pegawai.setEmail("deddy.kakunsi@gmail.com");
		pegawai.setTelepon("083247643198");
		
		pegawaiService.simpan(pegawai);
		assertEquals(countPegawai + 1, pegawaiRepository.count());
		assertEquals(countPenduduk + 1, pendudukRepository.count());
		
		countRiwayatPangkat = riwayatPangkatRepository.count();

		pegawaiService.promosi(pegawai, Pangkat.IIIA, DateUtil.getDate("12-01-2013"), null, "001/SK/2015");
		
		//assertEquals(Pangkat.IIIA, pegawai.getPangkat());
		assertEquals(countRiwayatPangkat + 1, riwayatPangkatRepository.count());
		
		long countJabatan = jabatanRepository.count();
		
		jabatanKaSubBagDatabase = new Jabatan();
		jabatanKaSubBagDatabase.setEselon(Eselon.Va);
		jabatanKaSubBagDatabase.setPangkat(Pangkat.IIIC);
		jabatanKaSubBagDatabase.setUnitKerja(unitKerja);
		jabatanKaSubBagDatabase.setNama("Kepala Sub Bagian Database");
		
		jabatanService.simpan(jabatanKaSubBagDatabase);
		assertEquals(countJabatan + 1, jabatanRepository.count());
		
		Jabatan jabatanKaSubBagJaringan = new Jabatan();
		jabatanKaSubBagJaringan.setEselon(Eselon.Va);
		jabatanKaSubBagJaringan.setPangkat(Pangkat.IIIC);
		jabatanKaSubBagJaringan.setUnitKerja(unitKerja);
		jabatanKaSubBagJaringan.setNama("Kepala Sub Bagian Jaringan");
		
		jabatanService.simpan(jabatanKaSubBagJaringan);
		assertEquals(countJabatan + 2, jabatanRepository.count());

		countRiwayatJabatan = riwayatJabatanRepository.count();

		pegawaiService.promosi(pegawai, jabatanKaSubBagJaringan, DateUtil.getDate("12-01-2013"), null, "001/SK/2015");
		
		assertEquals(countRiwayatJabatan + 1, riwayatJabatanRepository.count());
		
		UnitKerja subUnitKerja = new UnitKerja(unitKerja);
		subUnitKerja.setNama("SubUnit Pengelolaan Data Elektronik");
		subUnitKerja.setSingkatan("SubUnit BPDE");
		subUnitKerja.setTipe(TipeUnitKerja.BAGIAN);
		unitKerjaService.simpan(subUnitKerja);
		
		Pegawai pegawai2 = new Pegawai();
		pegawai2.setNik("7171070512910001");
		pegawai2.setNip("090213015");
		pegawai2.setNama("Debra Tiwow");
		pegawai2.setPassword("dtiwow");
		pegawai2.setTanggalLahir(DateUtil.getDate(1992, Month.JANUARY, 10));
		pegawai2.setUnitKerja(subUnitKerja);
		pegawai2.setEmail("debra.tiwow@gmail.com");
		pegawai2.setTelepon("083247643190");
		pegawaiService.simpan(pegawai2);
		
		assertEquals(countPegawai + 2, pegawaiRepository.count());
		assertEquals(countPenduduk + 2, pendudukRepository.count());
	}
	
	@Test(expected = PersistenceException.class)
	public void test_simpan_duplicate_pegawai() {
		Pegawai pegawai = new Pegawai();
		pegawai.setNik("7171070512910002");
		pegawai.setNip("090213016");
		pegawai.setNama("Deddy Christoper Kakunsi");
		pegawai.setPassword("dkakunsi");
		pegawai.setTanggalLahir(DateUtil.getDate("12-05-1991"));
		pegawai.setEmail("deddy.kakunsi@gmail.com");
		pegawai.setTelepon("083247643198");
		
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
		pegawai.setEmail("deddy.kakunsi@gmail.com");
		pegawai.setTelepon("083247643198");
		
		pegawaiService.simpan(pegawai);
	}
	
	@Test
	public void test_edit() {
		pegawai.setNama("Deddy Kakunsi");
		pegawai.setPassword("dking");
		pegawai.setTelepon("083247643190");
		
		pegawaiService.simpan(pegawai);
		
		assertEquals("Deddy Kakunsi", pegawai.getNama());
		assertEquals("7171070512910002", pegawai.getNik());
		assertEquals("090213016", pegawai.getNip());
	}
	
	@Test
	public void test_mutasi() throws IdenticRelationshipException {
		UnitKerja unitKerja = new UnitKerja();
		unitKerja.setNama("Umum");
		unitKerja.setSingkatan("BUMUM");
		unitKerja.setTipe(TipeUnitKerja.BAGIAN);
		
		unitKerjaService.simpan(unitKerja);
		
		pegawaiService.mutasi(pegawai, unitKerja);
		
		assertEquals(unitKerja, pegawai.getUnitKerja());
	}
	
	@Test
	public void test_promosi_pangkat() throws IdenticRelationshipException, NullCollectionException, NoPangkatException {
		pegawai = pegawaiService.promosi(pegawai.getNip(), Pangkat.IIIB, DateUtil.getDate("12-01-2013"), null, "001/SK/2015");

		assertEquals(Pangkat.IIIB, pegawai.getPangkat());
		assertEquals(countRiwayatPangkat + 2, riwayatPangkatRepository.count());
	}
	
	@Test
	public void test_promosi_jabatan() throws IdenticRelationshipException, NullCollectionException, NoJabatanException {
		pegawai = pegawaiService.promosi(pegawai.getNip(), jabatanKaSubBagDatabase, DateUtil.getDate("12-01-2014"), null, "002/SK/2015");

		assertEquals(jabatanKaSubBagDatabase, pegawai.getJabatan());
		assertEquals(countRiwayatJabatan + 2, riwayatJabatanRepository.count());
	}
	
	@Test
	public void test_get_by_pangkat() {
		List<Pegawai> list = pegawaiService.get(Pangkat.IIIA);
		
		assertNotEquals(0, list.size());
	}
	
	@Test
	public void test_get_by_eselon() {
		List<Pegawai> list = pegawaiService.get(Eselon.Va);
		
		assertNotEquals(0, list.size());
	}
	
	@Test
	public void test_get_by_unit_kerja() {
		List<Pegawai> list = pegawaiService.getByUnitKerja(unitKerja.getId());
		
		assertEquals(2, list.size());
	}

	@Test
	public void test_get() {
		assertNotEquals(0, riwayatPangkatRepository.count());
		assertNotEquals(0, riwayatJabatanRepository.count());

		Pegawai pegawai = pegawaiService.getByNip("090213016");
		assertNotNull(pegawai.getPenduduk());
		assertEquals("Deddy Christoper Kakunsi", pegawai.getNama());
		assertNotNull(pegawai.getUnitKerja());
		assertEquals("Pengelolaan Data Elektronik", pegawai.getUnitKerja().getNama());

		assertNotNull(pegawai);
		assertNotEquals(0, pegawai.getDaftarPangkat().size());
		assertNotEquals(0, pegawai.getDaftarJabatan().size());
	}
	
	@Test
	public void test_hapus() {
		pegawaiService.hapus(pegawai);
		assertEquals(countPegawai + 1, pegawaiRepository.count());
		assertEquals(countPenduduk + 1, pendudukRepository.count());
	}
	
	@Test
	public void test_hapus_by_nip() {
		pegawaiService.hapus(pegawai.getNip());
		assertEquals(countPegawai + 1, pegawaiRepository.count());
		assertEquals(countPenduduk + 1, pendudukRepository.count());
	}
}
