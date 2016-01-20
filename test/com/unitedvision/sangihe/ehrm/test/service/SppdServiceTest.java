package com.unitedvision.sangihe.ehrm.test.service;

import static org.junit.Assert.*;

import java.time.Month;
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
import com.unitedvision.sangihe.ehrm.absensi.Kalendar;
import com.unitedvision.sangihe.ehrm.absensi.repository.KalendarRepository;
import com.unitedvision.sangihe.ehrm.absensi.repository.TugasLuarRepository;
import com.unitedvision.sangihe.ehrm.simpeg.Pegawai;
import com.unitedvision.sangihe.ehrm.simpeg.PegawaiService;
import com.unitedvision.sangihe.ehrm.simpeg.UnitKerja;
import com.unitedvision.sangihe.ehrm.simpeg.UnitKerjaService;
import com.unitedvision.sangihe.ehrm.simpeg.UnitKerja.TipeUnitKerja;
import com.unitedvision.sangihe.ehrm.sppd.PemegangTugas;
import com.unitedvision.sangihe.ehrm.sppd.Pengikut;
import com.unitedvision.sangihe.ehrm.sppd.RekapSppd;
import com.unitedvision.sangihe.ehrm.sppd.Sppd;
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
	@Autowired
	private TugasLuarRepository tugasLuarRepository;
	@Autowired
	private KalendarRepository kalendarRepository;

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
		unitKerja.setSingkatan("BPDE2");
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
		
		suratTugas = new SuratTugas();
		suratTugas.setJumlahHari(3);
		suratTugas.setMaksud("Konsultasi Teknis Pengembangan Rencana Induk TIK");
		suratTugas.setTujuan("Manado");
		
		List<Pegawai> daftarPegawai = new ArrayList<>();
		daftarPegawai.add(pegawai);
		
		suratTugas = suratTugasService.ajukan(suratTugas, daftarPegawai);
		List<PemegangTugas> list = suratTugas.getDaftarPemegangTugas();
		pemegangTugas = list.get(0);
		
		List<Kalendar> daftarKalendar = new ArrayList<>();
		daftarKalendar.add(new Kalendar(DateUtil.getDate(2015, Month.JANUARY, 1)));
		daftarKalendar.add(new Kalendar(DateUtil.getDate(2015, Month.JANUARY, 2)));
		daftarKalendar.add(new Kalendar(DateUtil.getDate(2015, Month.JANUARY, 3)));
		daftarKalendar.add(new Kalendar(DateUtil.getDate(2015, Month.JANUARY, 4)));
		kalendarRepository.save(daftarKalendar);
	}
	
	@Test
	public void simpan() {
		Sppd sppd = new Sppd();
		sppd.setPemegangTugas(pemegangTugas);
		sppd.setNomor("001/SPPD/2015");
		sppd.setTingkat("Prioritas");
		
		sppd.setNomorDpa("001/DPA/2015");
		sppd.setKodeRekening("07.21.131.0089");
		
		sppd.setAsal("Tahuna");
		sppd.setModaTransportasi("Kapal Laut");
		sppd.setTanggalBerangkat(DateUtil.getDate(2015, Month.JANUARY, 1));

		Pengikut pengikut = new Pengikut();
		pengikut.setNama("Lusye Landeng");
		pengikut.setTanggalLahir(DateUtil.getDate(1992, 6, 30));
		
		sppd.addPengikut(pengikut);
		
		sppd = sppdService.simpan(sppd);
		
		assertEquals(countSppd + 1, sppdRepository.count());
		assertEquals(countPengikut + 1, pengikutRepository.count());
		assertNotEquals(0, sppd.getDaftarPengikut().size());
		
		assertEquals(pemegangTugas.getSuratTugas().getJumlahHari(), tugasLuarRepository.countBySppd(sppd));
	}
	
	@SuppressWarnings("deprecation")
	@Test
	public void test_rekap() {
		
		List<RekapSppd> daftarRekap = sppdService.rekap(2015);
		assertNotEquals(0, daftarRekap.size());
		
		for(RekapSppd rekap : daftarRekap)
			System.out.println(rekap);
	}
}
