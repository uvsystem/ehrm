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
import com.unitedvision.sangihe.ehrm.duk.Penduduk.Kontak;
import com.unitedvision.sangihe.ehrm.simpeg.Pegawai;
import com.unitedvision.sangihe.ehrm.simpeg.PegawaiService;
import com.unitedvision.sangihe.ehrm.simpeg.UnitKerja;
import com.unitedvision.sangihe.ehrm.simpeg.UnitKerjaService;
import com.unitedvision.sangihe.ehrm.simpeg.UnitKerja.TipeUnitKerja;
import com.unitedvision.sangihe.ehrm.sppd.PemegangTugas;
import com.unitedvision.sangihe.ehrm.sppd.Pengikut;
import com.unitedvision.sangihe.ehrm.sppd.Sppd;
import com.unitedvision.sangihe.ehrm.sppd.Sppd.Anggaran;
import com.unitedvision.sangihe.ehrm.sppd.Sppd.Perjalanan;
import com.unitedvision.sangihe.ehrm.sppd.SppdService;
import com.unitedvision.sangihe.ehrm.sppd.SuratTugas;
import com.unitedvision.sangihe.ehrm.sppd.SuratTugasService;
import com.unitedvision.sangihe.ehrm.sppd.repository.PengikutRepository;
import com.unitedvision.sangihe.ehrm.sppd.repository.SppdRepository;

@RunWith (SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {ApplicationConfig.class})
@Transactional
@TransactionConfiguration (defaultRollback = true)
public class SppdServiceTest {

	@Autowired
	private SppdService sppdService;
	@Autowired
	private SuratTugasService suratTugasService;
	@Autowired
	private UnitKerjaService unitKerjaService;
	@Autowired
	private PegawaiService pegawaiService;
	@Autowired
	private SppdRepository sppdRepository;
	@Autowired
	private PengikutRepository pengikutRepository;

	private UnitKerja unitKerja;
	private Pegawai pegawai;
	private SuratTugas suratTugas;
	private PemegangTugas pemegangTugas;
	private long countSppd;
	private long countPengikut;

	@Before
	public void setup() {
		countSppd = sppdRepository.count();
		countPengikut = pengikutRepository.count();
		
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
		
		Kontak kontak = new Kontak();
		kontak.setEmail("deddy.kakunsi@gmail.com");
		kontak.setTelepon("083247643198");
		pegawai.setKontak(kontak);
		
		pegawaiService.simpan(pegawai);
		
		suratTugas = new SuratTugas();
		suratTugas.setJumlahHari(3);
		suratTugas.setMaksud("Konsultasi Teknis Pengembangan Rencana Induk TIK");
		suratTugas.setTujuan("Manado");
		
		List<Pegawai> daftarPegawai = new ArrayList<>();
		daftarPegawai.add(pegawai);
		
		suratTugas = suratTugasService.simpan(suratTugas, daftarPegawai);
		List<PemegangTugas> list = suratTugas.getDaftarPemegangTugas();
		pemegangTugas = list.get(0);
	}
	
	@Test
	public void simpan() {
		Sppd sppd = new Sppd();
		sppd.setPemegangTugas(pemegangTugas);
		sppd.setNomor("001/SPPD/2015");
		sppd.setTingkat("Prioritas");
		
		Anggaran anggaran = sppd.new Anggaran();
		anggaran.setNomorDpa("001/DPA/2015");
		anggaran.setKodeRekening("07.21.131.0089");
		sppd.setAnggaran(anggaran);
		
		Perjalanan perjalanan = sppd.new Perjalanan();
		perjalanan.setAsal("Tahuna");
		perjalanan.setModaTransportasi("Kapal Laut");
		perjalanan.setTanggalBerangkat(DateUtil.getNow());
		sppd.setPerjalanan(perjalanan);

		Pengikut pengikut = new Pengikut();
		pengikut.setNama("Lusye Landeng");
		pengikut.setTanggalLahir(DateUtil.getDate(1992, 6, 30));
		
		sppd.addPengikut(pengikut);
		
		sppdService.simpan(sppd);
		
		assertEquals(countSppd + 1, sppdRepository.count());
		assertEquals(countPengikut + 1, pengikutRepository.count());
	}
}
