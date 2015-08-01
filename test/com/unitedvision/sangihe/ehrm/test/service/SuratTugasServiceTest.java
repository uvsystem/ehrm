package com.unitedvision.sangihe.ehrm.test.service;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

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
import com.unitedvision.sangihe.ehrm.simpeg.Pegawai;
import com.unitedvision.sangihe.ehrm.simpeg.PegawaiService;
import com.unitedvision.sangihe.ehrm.simpeg.UnitKerja;
import com.unitedvision.sangihe.ehrm.simpeg.UnitKerjaService;
import com.unitedvision.sangihe.ehrm.simpeg.UnitKerja.TipeUnitKerja;
import com.unitedvision.sangihe.ehrm.sppd.PemegangTugas;
import com.unitedvision.sangihe.ehrm.sppd.SuratTugas;
import com.unitedvision.sangihe.ehrm.sppd.SuratTugasService;
import com.unitedvision.sangihe.ehrm.sppd.SuratTugas.Status;
import com.unitedvision.sangihe.ehrm.sppd.repository.PemegangTugasRepository;
import com.unitedvision.sangihe.ehrm.sppd.repository.SuratTugasRepository;

@RunWith (SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {ApplicationConfig.class})
@Transactional
@TransactionConfiguration (defaultRollback = true)
public class SuratTugasServiceTest {

	@Autowired
	private SuratTugasService suratTugasService;
	@Autowired
	private UnitKerjaService unitKerjaService;
	@Autowired
	private PegawaiService pegawaiService;
	@Autowired
	private SuratTugasRepository suratTugasRepository;
	@Autowired
	private PemegangTugasRepository pemegangTugasRepository;

	private UnitKerja unitKerja;
	private Pegawai pegawai;
	private SuratTugas suratTugas;
	private long countSuratTugas;
	private long countPemegangTugas;
	
	@Before
	public void setup() {
		countSuratTugas = suratTugasRepository.count();
		countPemegangTugas = pemegangTugasRepository.count();
		
		unitKerja = new UnitKerja();
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
		pegawai.setEmail("deddy.kakunsi@gmail.com");
		pegawai.setTelepon("083247643198");
		
		pegawaiService.simpan(pegawai);
		
		suratTugas = new SuratTugas();
		suratTugas.setNomor("001-STG-01-2015");
		suratTugas.setJumlahHari(3);
		suratTugas.setMaksud("Konsultasi Teknis Pengembangan Rencana Induk TIK");
		suratTugas.setTujuan("Manado");
		
		List<Pegawai> daftarPegawai = new ArrayList<>();
		daftarPegawai.add(pegawai);
		
		suratTugas= suratTugasService.ajukan(suratTugas, daftarPegawai);
		List<PemegangTugas> list = suratTugas.getDaftarPemegangTugas();

		assertNotEquals(0, list.size());
		assertEquals(1, suratTugas.getDaftarPemegangTugas().size());
		assertEquals(countSuratTugas + 1, suratTugasRepository.count());
		assertEquals(countPemegangTugas + 1, pemegangTugasRepository.count());
		assertEquals(Status.PENDING, suratTugas.getStatus());
	}
	
	@Test
	public void test_tambah_pegawai() {
		Pegawai pegawai2 = new Pegawai();
		pegawai2.setNik("7171070512910001");
		pegawai2.setNip("090213015");
		pegawai2.setNama("Josep Kakunsi");
		pegawai2.setPassword("dkakunsi");
		pegawai2.setTanggalLahir(DateUtil.getDate("12-05-1991"));
		pegawai2.setUnitKerja(unitKerja);
		pegawai.setEmail("dkakunsi@gmail.com");
		pegawai.setTelepon("083247643198");
		
		pegawaiService.simpan(pegawai2);
		
		suratTugas = suratTugasService.tambahPegawai(suratTugas, pegawai2);
		assertEquals(2, suratTugas.getDaftarPemegangTugas().size());
		assertEquals(countSuratTugas + 1, suratTugasRepository.count());
		assertEquals(countPemegangTugas + 2, pemegangTugasRepository.count());
	}
	
	@Test
	public void test_izinkan_pengajuan() {
		suratTugas = suratTugasService.izinkanPengajuan(suratTugas);

		assertEquals(1, suratTugas.getDaftarPemegangTugas().size());
		assertEquals(Status.DITERIMA, suratTugas.getStatus());
	}
	
	@Test
	public void test_tolak_pengajuan() {
		suratTugas = suratTugasService.tolakPengajuan(suratTugas);

		assertEquals(1, suratTugas.getDaftarPemegangTugas().size());
		assertEquals(Status.DITOLAK, suratTugas.getStatus());
	}
	
	@Test
	public void test_get_pending() {
		List<SuratTugas> daftarSuratTugas = suratTugasService.getByStatus(SuratTugas.Status.PENDING);
		
		assertNotEquals(0, daftarSuratTugas.size());
	}
}
