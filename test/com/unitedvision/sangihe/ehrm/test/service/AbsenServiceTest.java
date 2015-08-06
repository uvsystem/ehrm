package com.unitedvision.sangihe.ehrm.test.service;

import static org.junit.Assert.*;

import java.sql.Date;
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
import com.unitedvision.sangihe.ehrm.absensi.Absen.Detail;
import com.unitedvision.sangihe.ehrm.absensi.AbsenException;
import com.unitedvision.sangihe.ehrm.absensi.AbsenService;
import com.unitedvision.sangihe.ehrm.absensi.Cuti;
import com.unitedvision.sangihe.ehrm.absensi.Hadir;
import com.unitedvision.sangihe.ehrm.absensi.Izin;
import com.unitedvision.sangihe.ehrm.absensi.KalendarService;
import com.unitedvision.sangihe.ehrm.absensi.RekapAbsen;
import com.unitedvision.sangihe.ehrm.absensi.Sakit;
import com.unitedvision.sangihe.ehrm.absensi.repository.CutiRepository;
import com.unitedvision.sangihe.ehrm.absensi.repository.HadirRepository;
import com.unitedvision.sangihe.ehrm.absensi.repository.IzinRepository;
import com.unitedvision.sangihe.ehrm.absensi.repository.SakitRepository;
import com.unitedvision.sangihe.ehrm.absensi.repository.TugasLuarRepository;
import com.unitedvision.sangihe.ehrm.simpeg.Pegawai;
import com.unitedvision.sangihe.ehrm.simpeg.PegawaiService;
import com.unitedvision.sangihe.ehrm.simpeg.UnitKerja;
import com.unitedvision.sangihe.ehrm.simpeg.UnitKerjaService;
import com.unitedvision.sangihe.ehrm.simpeg.UnitKerja.TipeUnitKerja;

@RunWith (SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {ApplicationConfig.class})
@Transactional
@TransactionConfiguration (defaultRollback = true)
public class AbsenServiceTest {

	@Autowired
	private AbsenService absenService;
	@Autowired
	private UnitKerjaService unitKerjaService;
	@Autowired
	private PegawaiService pegawaiService;
	@Autowired
	private KalendarService kalendarService;
	@Autowired
	private HadirRepository hadirRepository;
	@Autowired
	private TugasLuarRepository tugasLuarRepository;
	@Autowired
	private SakitRepository sakitRepository;
	@Autowired
	private IzinRepository izinRepository;
	@Autowired
	private CutiRepository cutiRepository;
	
	private Pegawai pegawai;
	
	@Before
	public void setup() {
		UnitKerja unitKerja = new UnitKerja();
		unitKerja.setNama("Unit Kerja");
		unitKerja.setSingkatan("UK");
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
		
		kalendarService.tambah(DateUtil.getDate());
		kalendarService.tambah(DateUtil.getDate(2015, Month.JANUARY, 1));
		kalendarService.tambah(DateUtil.getDate(2015, Month.JANUARY, 2));
		kalendarService.tambah(DateUtil.getDate(2015, Month.JANUARY, 3));
	}

	@Test
	public void apel_pagi() throws AbsenException {
		Hadir hadir = absenService.apelPagi("090213010", DateUtil.getDate(), DateUtil.getTime(7, 0, 0));

		assertNotNull(hadir);
		assertNotEquals(0, hadir.getId());
		assertNotNull(hadir.getPagi());
	}
	
	@Test
	public void apel_pagi_list() throws AbsenException {
		List<Detail> daftarAbsen = new ArrayList<>();
		
		daftarAbsen.add(new Detail("090213010", "01-01-2015", "07:00:00"));
		daftarAbsen.add(new Detail("090213010", "01-02-2015", "07:00:00"));
		daftarAbsen.add(new Detail("090213010", "01-03-2015", "07:00:00"));
		
		absenService.apelPagi(daftarAbsen);
		
		assertEquals(3, hadirRepository.count());
	}

	@Test
	public void pengecekan_satu() throws AbsenException {
		Hadir hadir = absenService.pengecekanSatu("090213010", DateUtil.getDate(), DateUtil.getTime(11, 0, 0));

		assertNotNull(hadir);
		assertNotEquals(0, hadir.getId());
		assertNotNull(hadir.getPengecekanPertama());
		assertNull(hadir.getPagi());
	}

	@Test
	public void pengecekan_dua() throws AbsenException {
		Hadir hadir = absenService.pengecekanDua("090213010", DateUtil.getDate(), DateUtil.getTime(13, 0, 0));

		assertNotNull(hadir);
		assertNotEquals(0, hadir.getId());
		assertNotNull(hadir.getPengecekanKedua());
		assertNull(hadir.getPagi());
	}

	@Test
	public void apel_sore() throws AbsenException {
		Hadir hadir = absenService.apelSore("090213010", DateUtil.getDate(), DateUtil.getTime(16, 0, 0));

		assertNotNull(hadir);
		assertNotEquals(0, hadir.getId());
		assertNotNull(hadir.getSore());
		assertNull(hadir.getPagi());
	}

	@Test
	public void sakit() {
		String penyakit = "Demam Berdarah";
		Sakit sakit = absenService.tambahSakit("090213010", DateUtil.getDate(), penyakit);
		
		assertNotNull(sakit);
		assertNotEquals(0, sakit.getId());
		assertEquals(penyakit, sakit.getPenyakit());
	}

	@Test
	public void izin() {
		String alasan = "Kedukaan";
		Izin izin = absenService.tambahIzin("090213010", DateUtil.getDate(), alasan);
		
		assertNotNull(izin);
		assertNotEquals(0, izin.getId());
		assertEquals(alasan, izin.getAlasan());
	}

	@Test
	public void cuti() {
		String jenisCuti = "Hamil";
		Cuti cuti = absenService.tambahCuti("090213010", DateUtil.getDate(), jenisCuti);
		
		assertNotNull(cuti);
		assertNotEquals(0, cuti.getId());
		assertEquals(jenisCuti, cuti.getJenisCuti());
	}
	
	@Test
	public void test_rekap() {
//		String jenisCuti = "Hamil";
//		Cuti cuti = absenService.tambahCuti("090213010", DateUtil.getDate(), jenisCuti);
//		assertNotEquals(0, cuti.getId());
		
		Date tanggalAwal = DateUtil.getFirstDate();
		Date tanggalAkhir = DateUtil.getLastDate();
		List<RekapAbsen> daftarRekap = absenService.rekapByUnitKerja("SETDA", tanggalAwal, tanggalAkhir);
		assertNotEquals(0, daftarRekap.size());
		
		for(RekapAbsen rekap : daftarRekap)
			System.out.println(rekap);
	}
}
