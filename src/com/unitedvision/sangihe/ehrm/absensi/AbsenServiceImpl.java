package com.unitedvision.sangihe.ehrm.absensi;

import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.PersistenceException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.unitedvision.sangihe.ehrm.DateUtil;
import com.unitedvision.sangihe.ehrm.absensi.Absen.Detail;
import com.unitedvision.sangihe.ehrm.absensi.Hadir.Jenis;
import com.unitedvision.sangihe.ehrm.absensi.repository.CutiRepository;
import com.unitedvision.sangihe.ehrm.absensi.repository.HadirRepository;
import com.unitedvision.sangihe.ehrm.absensi.repository.IzinRepository;
import com.unitedvision.sangihe.ehrm.absensi.repository.KalendarRepository;
import com.unitedvision.sangihe.ehrm.absensi.repository.SakitRepository;
import com.unitedvision.sangihe.ehrm.absensi.repository.TugasLuarRepository;
import com.unitedvision.sangihe.ehrm.simpeg.Pegawai;
import com.unitedvision.sangihe.ehrm.simpeg.UnitKerja;
import com.unitedvision.sangihe.ehrm.simpeg.repository.PegawaiRepository;
import com.unitedvision.sangihe.ehrm.sppd.Sppd;
import com.unitedvision.sangihe.ehrm.sppd.repository.SppdRepository;

@Service
@Transactional(readOnly = true)
public class AbsenServiceImpl implements AbsenService {

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
	@Autowired
	private PegawaiRepository pegawaiRepository;
	@Autowired
	private SppdRepository sppdRepository;
	@Autowired
	private KalendarRepository kalendarRepository;
	
	/**
	 * Ambil absen jika sudah ada, buat baru jika tidak ada.
	 * 
	 * @param nip
	 * @param tanggal jika null, pakai tanggal hari ini
	 * @return
	 * @throws EntityNotExistException 
	 */
	private Hadir getHadir(String nip, Date tanggal) {
		
		Pegawai pegawai = pegawaiRepository.findByNip(nip);

		if (tanggal == null)
			tanggal = DateUtil.getDate();
		
		Hadir hadir;
		
		try {
			
			hadir = hadirRepository.findByPegawaiAndKalendar_Tanggal(pegawai, tanggal);
			
		} catch (PersistenceException e) {
			
			hadir = new Hadir();
			hadir.setPegawai(pegawai);
			hadir.setTanggal(tanggal);
		}
		
		return hadir;
	}
	
	private List<Hadir> createDaftarHadir(Jenis jenis, List<Detail> daftarAbsen) {
		List<Hadir> daftarHadir = new ArrayList<>();
		for (Detail detail : daftarAbsen) {

			try {
				
				Hadir hadir = null;
				
				if (jenis.equals(Jenis.PAGI)) {
					hadir = createPagi(detail.getNip(), detail.getTanggal(), detail.getJam());
				} else if (jenis.equals(Jenis.PENGECEKAN_SATU)) {
					hadir = createPengecekanSatu(detail.getNip(), detail.getTanggal(), detail.getJam());
				} else if (jenis.equals(Jenis.PENGECEKAN_DUA)) {
					hadir = createPengecekanDua(detail.getNip(), detail.getTanggal(), detail.getJam());
				} else if (jenis.equals(Jenis.SORE)) {
					hadir = createSore(detail.getNip(), detail.getTanggal(), detail.getJam());
				} else {
					throw new AbsenException("Lanjut");
				}
				
				daftarHadir.add(hadir);
			} catch(AbsenException e){ }
		}
		
		return daftarHadir;
	}

	@Override
	@Transactional(readOnly = false)
	public Hadir apelPagi(String nip, Date tanggal, Time jam) throws AbsenException {
		Hadir hadir = createPagi(nip, tanggal, jam);
		
		return hadirRepository.save(hadir);
	}
	
	private Hadir createPagi(String nip, Date tanggal, Time jam) throws AbsenException {

		// Buat entitas absen hadir yang baru
		Hadir hadir= getHadir(nip, tanggal);
		
		// Jika absen pagi sudah ada, tolak perubahan.
		if (hadir.getPagi() != null)
			throw new AbsenException("Sudah absen pagi");

		if (jam == null)
			jam = DateUtil.getTime();
		
		hadir.setPagi(jam);

		return hadir;
	}

	@Override
	public List<Hadir> apelPagi(List<Detail> daftarAbsen) throws AbsenException {
		List<Hadir> daftarHadir = createDaftarHadir(Jenis.PAGI, daftarAbsen);
		
		for (Hadir hadir : daftarHadir) {
			System.out.println("TEST");
			System.out.println(hadir);
		}
		
		return hadirRepository.save(daftarHadir);
	}
	
	@Override
	@Transactional(readOnly = false)
	public Hadir pengecekanSatu(String nip, Date tanggal, Time jam) throws AbsenException {
		Hadir hadir = createPengecekanSatu(nip, tanggal, jam);
		
		return hadirRepository.save(hadir);
	}
	
	private Hadir createPengecekanSatu(String nip, Date tanggal, Time jam) throws AbsenException {

		Hadir hadir= getHadir(nip, tanggal);
		
		// Jika absen pengecekan 1 sudah ada, tolak perubahan.
		if (hadir.getPengecekanPertama() != null)
			throw new AbsenException("Sudah absen pengecekan pertama");

		if (jam == null)
			jam = DateUtil.getTime();
		
		hadir.setPengecekanPertama(jam);
		
		return hadir;
	}

	@Override
	public List<Hadir> pengecekanSatu(List<Detail> daftarAbsen) throws AbsenException {
		List<Hadir> daftarHadir = createDaftarHadir(Jenis.PENGECEKAN_SATU, daftarAbsen);
		
		return hadirRepository.save(daftarHadir);
	}
	
	@Override
	@Transactional(readOnly = false)
	public Hadir pengecekanDua(String nip, Date tanggal, Time jam) throws AbsenException {
		Hadir hadir = createPengecekanDua(nip, tanggal, jam);
		
		return hadirRepository.save(hadir);
	}
	
	private Hadir createPengecekanDua(String nip, Date tanggal, Time jam) throws AbsenException {

		Hadir hadir= getHadir(nip, tanggal);
		
		// Jika absen pengecekan 1 sudah ada, tolak perubahan.
		if (hadir.getPengecekanKedua() != null)
			throw new AbsenException("Sudah absen pengecekan kedua");

		if (jam == null)
			jam = DateUtil.getTime();
		
		hadir.setPengecekanKedua(jam);
		
		return hadir;
	}

	@Override
	public List<Hadir> pengecekanDua(List<Detail> daftarAbsen) throws AbsenException {
		List<Hadir> daftarHadir = createDaftarHadir(Jenis.PENGECEKAN_DUA, daftarAbsen);
		
		return hadirRepository.save(daftarHadir);
	}

	@Override
	@Transactional(readOnly = false)
	public Hadir apelSore(String nip, Date tanggal, Time jam) throws AbsenException {
		Hadir hadir = createSore(nip, tanggal, jam);
		
		return hadirRepository.save(hadir);
	}
	
	private Hadir createSore(String nip, Date tanggal, Time jam) throws AbsenException {

		Hadir hadir= getHadir(nip, tanggal);
		
		// Jika absen pengecekan 1 sudah ada, tolak perubahan.
		if (hadir.getSore() != null)
			throw new AbsenException("Sudah absen sore");

		if (jam == null)
			jam = DateUtil.getTime();
		
		hadir.setSore(jam);
		
		return hadir;
	}

	@Override
	public List<Hadir> apelSore(List<Detail> daftarAbsen) throws AbsenException {
		List<Hadir> daftarHadir = createDaftarHadir(Jenis.SORE, daftarAbsen);
		
		return hadirRepository.save(daftarHadir);
	}

	@Override
	@Transactional(readOnly = false)
	public TugasLuar tambahTugasLuar(String nip, Date tanggal, String nomorSppd) {
		Pegawai pegawai = pegawaiRepository.findByNip(nip);
		Sppd sppd = sppdRepository.findByNomor(nomorSppd);
		
		TugasLuar tugasLuar = new TugasLuar();
		tugasLuar.setPegawai(pegawai);
		tugasLuar.setTanggal(tanggal);
		tugasLuar.setSppd(sppd);
		
		return tugasLuarRepository.save(tugasLuar);
	}

	@Override
	@Transactional(readOnly = false)
	public List<TugasLuar> tambahTugasLuar(Sppd sppd) {
		Date tanggalBerangkat = sppd.getTanggalBerangkat();
		Date tanggalKembali = sppd.getTanggalKembali();
		
		List<Kalendar> daftarTanggal = kalendarRepository.findByTanggalBetween(tanggalBerangkat, tanggalKembali);
		List<TugasLuar> daftarTugasLuar = new ArrayList<>();
		for (Kalendar kalendar : daftarTanggal) {
			daftarTugasLuar.add(new TugasLuar(sppd, kalendar));
		}
		
		return tugasLuarRepository.save(daftarTugasLuar);
	}
	
	@Override
	@Transactional(readOnly = false)
	public Sakit tambahSakit(String nip, Date tanggal, String penyakit) {
		Pegawai pegawai = pegawaiRepository.findByNip(nip);

		Sakit sakit = new Sakit();
		sakit.setPegawai(pegawai);
		sakit.setPenyakit(penyakit);
		sakit.setTanggal(tanggal);
		
		return sakitRepository.save(sakit);
	}

	@Override
	@Transactional(readOnly = false)
	public Izin tambahIzin(String nip, Date tanggal, String alasan) {
		Pegawai pegawai = pegawaiRepository.findByNip(nip);

		Izin izin = new Izin();
		izin.setPegawai(pegawai);
		izin.setAlasan(alasan);
		izin.setTanggal(tanggal);
		
		return izinRepository.save(izin);
	}

	@Override
	@Transactional(readOnly = false)
	public Cuti tambahCuti(String nip, Date tanggal, String jenisCuti) {
		Pegawai pegawai = pegawaiRepository.findByNip(nip);

		Cuti cuti = new Cuti();
		cuti.setPegawai(pegawai);
		cuti.setJenisCuti(jenisCuti);
		cuti.setTanggal(tanggal);
		
		return cutiRepository.save(cuti);
	}

	@Override
	public List<Hadir> getHadir(Pegawai pegawai, Date tanggalAwal, Date tanggalAkhir) {
		return hadirRepository.findByPegawaiAndKalendar_TanggalBetween(pegawai, tanggalAwal, tanggalAkhir);
	}

	@Override
	public List<Hadir> getHadir(UnitKerja unitKerja, Date tanggalAwal, Date tanggalAkhir) {
		return hadirRepository.findByPegawai_UnitKerjaAndKalendar_TanggalBetween(unitKerja, tanggalAwal, tanggalAkhir);
	}

	@Override
	public List<TugasLuar> getTugasLuar(Pegawai pegawai, Date tanggalAwal, Date tanggalAkhir) {
		return tugasLuarRepository.findByPegawaiAndKalendar_TanggalBetween(pegawai, tanggalAwal, tanggalAkhir);
	}

	@Override
	public List<TugasLuar> getTugasLuar(UnitKerja unitKerja, Date tanggalAwal, Date tanggalAkhir) {
		return tugasLuarRepository.findByPegawai_UnitKerjaAndKalendar_TanggalBetween(unitKerja, tanggalAkhir, tanggalAkhir);
	}

	@Override
	public List<Sakit> getSakit(Pegawai pegawai, Date tanggalAwal, Date tanggalAkhir) {
		return sakitRepository.findByPegawaiAndKalendar_TanggalBetween(pegawai, tanggalAwal, tanggalAkhir);
	}

	@Override
	public List<Sakit> getSakit(UnitKerja unitKerja, Date tanggalAwal, Date tanggalAkhir) {
		return sakitRepository.findByPegawai_UnitKerjaAndKalendar_TanggalBetween(unitKerja, tanggalAwal, tanggalAkhir);
	}

	@Override
	public List<Izin> getIzin(Pegawai pegawai, Date tanggalAwal, Date tanggalAkhir) {
		return izinRepository.findByPegawaiAndKalendar_TanggalBetween(pegawai, tanggalAwal, tanggalAkhir);
	}

	@Override
	public List<Izin> getIzin(UnitKerja unitKerja, Date tanggalAwal, Date tanggalAkhir) {
		return izinRepository.findByPegawai_UnitKerjaAndKalendar_TanggalBetween(unitKerja, tanggalAwal, tanggalAkhir);
	}

	@Override
	public List<Cuti> getCuti(Pegawai pegawai, Date tanggalAwal, Date tanggalAkhir) {
		return cutiRepository.findByPegawaiAndKalendar_TanggalBetween(pegawai, tanggalAwal, tanggalAkhir);
	}

	@Override
	public List<Cuti> getCuti(UnitKerja unitKerja, Date tanggalAwal, Date tanggalAkhir) {
		return cutiRepository.findByPegawai_UnitKerjaAndKalendar_TanggalBetween(unitKerja, tanggalAwal, tanggalAkhir);
	}

}
