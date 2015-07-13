package com.unitedvision.sangihe.ehrm.absensi;

import java.sql.Date;
import java.sql.Time;
import java.util.List;

import javax.persistence.PersistenceException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.unitedvision.sangihe.ehrm.DateUtil;
import com.unitedvision.sangihe.ehrm.EntityNotExistException;
import com.unitedvision.sangihe.ehrm.absensi.repository.CutiRepository;
import com.unitedvision.sangihe.ehrm.absensi.repository.HadirRepository;
import com.unitedvision.sangihe.ehrm.absensi.repository.IzinRepository;
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
	
	/**
	 * Ambil absen jika sudah ada, buat baru jika tidak ada.
	 * 
	 * @param nip
	 * @param tanggal jika null, pakai tanggal hari ini
	 * @return
	 * @throws EntityNotExistException 
	 */
	private Hadir getHadir(String nip, Date tanggal) throws EntityNotExistException {
		
		Pegawai pegawai = pegawaiRepository.findByNip(nip);

		if (tanggal == null)
			tanggal = DateUtil.getNow();
		
		Hadir hadir;
		
		try {
			
			hadir = hadirRepository.findByPegawaiAndKalendar_Tanggal(pegawai, tanggal);
			
			if (hadir == null)
				throw new EntityNotExistException();
			
		} catch (EntityNotExistException | PersistenceException e) {
			
			hadir = new Hadir();
			hadir.setPegawai(pegawai);
			hadir.setTanggal(tanggal);
		}
		
		return hadir;
	}

	@Override
	@Transactional(readOnly = false)
	public Hadir apelPagi(String nip, Date tanggal, Time jam) throws AbsenException, EntityNotExistException {

		// Buat entitas absen hadir yang baru
		Hadir hadir= getHadir(nip, tanggal);
		
		// Jika absen pagi sudah ada, tolak perubahan.
		if (hadir.getPagi() != null)
			throw new AbsenException("Sudah absen pagi");

		if (jam == null)
			jam = DateUtil.getTime();
		
		hadir.setPagi(jam);
		
		return hadirRepository.save(hadir);
	}

	@Override
	@Transactional(readOnly = false)
	public Hadir pengecekanSatu(String nip, Date tanggal, Time jam) throws AbsenException, EntityNotExistException {

		Hadir hadir= getHadir(nip, tanggal);
		
		// Jika absen pengecekan 1 sudah ada, tolak perubahan.
		if (hadir.getPengecekanPertama() != null)
			throw new AbsenException("Sudah absen pengecekan pertama");

		if (jam == null)
			jam = DateUtil.getTime();
		
		hadir.setPengecekanPertama(jam);
		
		return hadirRepository.save(hadir);
	}

	@Override
	@Transactional(readOnly = false)
	public Hadir pengecekanDua(String nip, Date tanggal, Time jam) throws AbsenException, EntityNotExistException {

		Hadir hadir= getHadir(nip, tanggal);
		
		// Jika absen pengecekan 1 sudah ada, tolak perubahan.
		if (hadir.getPengecekanKedua() != null)
			throw new AbsenException("Sudah absen pengecekan kedua");

		if (jam == null)
			jam = DateUtil.getTime();
		
		hadir.setPengecekanKedua(jam);
		
		return hadirRepository.save(hadir);
	}

	@Override
	@Transactional(readOnly = false)
	public Hadir apelSore(String nip, Date tanggal, Time jam) throws AbsenException, EntityNotExistException {

		Hadir hadir= getHadir(nip, tanggal);
		
		// Jika absen pengecekan 1 sudah ada, tolak perubahan.
		if (hadir.getSore() != null)
			throw new AbsenException("Sudah absen sore");

		if (jam == null)
			jam = DateUtil.getTime();
		
		hadir.setSore(jam);
		
		return hadirRepository.save(hadir);
	}

	@Override
	@Transactional(readOnly = false)
	public TugasLuar tambahTugasLuar(String nip, Date tanggal, String nomorSppd) throws EntityNotExistException {
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
	public Sakit tambahSakit(String nip, Date tanggal, String penyakit) throws EntityNotExistException {
		Pegawai pegawai = pegawaiRepository.findByNip(nip);

		Sakit sakit = new Sakit();
		sakit.setPegawai(pegawai);
		sakit.setPenyakit(penyakit);
		sakit.setTanggal(tanggal);
		
		return sakitRepository.save(sakit);
	}

	@Override
	@Transactional(readOnly = false)
	public Izin tambahIzin(String nip, Date tanggal, String alasan) throws EntityNotExistException {
		Pegawai pegawai = pegawaiRepository.findByNip(nip);

		Izin izin = new Izin();
		izin.setPegawai(pegawai);
		izin.setAlasan(alasan);
		izin.setTanggal(tanggal);
		
		return izinRepository.save(izin);
	}

	@Override
	@Transactional(readOnly = false)
	public Cuti tambahCuti(String nip, Date tanggal, String jenisCuti) throws EntityNotExistException {
		Pegawai pegawai = pegawaiRepository.findByNip(nip);

		Cuti cuti = new Cuti();
		cuti.setPegawai(pegawai);
		cuti.setJenisCuti(jenisCuti);
		cuti.setTanggal(tanggal);
		
		return cutiRepository.save(cuti);
	}

	@Override
	public List<Hadir> getHadir(Pegawai pegawai, Date tanggalAwal, Date tanggalAkhir) throws EntityNotExistException {
		return hadirRepository.findByPegawaiAndKalendar_TanggalBetween(pegawai, tanggalAwal, tanggalAkhir);
	}

	@Override
	public List<Hadir> getHadir(UnitKerja unitKerja, Date tanggalAwal, Date tanggalAkhir) throws EntityNotExistException {
		return hadirRepository.findByPegawai_UnitKerjaAndKalendar_TanggalBetween(unitKerja, tanggalAwal, tanggalAkhir);
	}

	@Override
	public List<TugasLuar> getTugasLuar(Pegawai pegawai, Date tanggalAwal, Date tanggalAkhir) throws EntityNotExistException {
		return tugasLuarRepository.findByPegawaiAndKalendar_TanggalBetween(pegawai, tanggalAwal, tanggalAkhir);
	}

	@Override
	public List<TugasLuar> getTugasLuar(UnitKerja unitKerja, Date tanggalAwal, Date tanggalAkhir) throws EntityNotExistException {
		return tugasLuarRepository.findByPegawai_UnitKerjaAndKalendar_TanggalBetween(unitKerja, tanggalAkhir, tanggalAkhir);
	}

	@Override
	public List<Sakit> getSakit(Pegawai pegawai, Date tanggalAwal, Date tanggalAkhir) throws EntityNotExistException {
		return sakitRepository.findByPegawaiAndKalendar_TanggalBetween(pegawai, tanggalAwal, tanggalAkhir);
	}

	@Override
	public List<Sakit> getSakit(UnitKerja unitKerja, Date tanggalAwal, Date tanggalAkhir) throws EntityNotExistException {
		return sakitRepository.findByPegawai_UnitKerjaAndKalendar_TanggalBetween(unitKerja, tanggalAwal, tanggalAkhir);
	}

	@Override
	public List<Izin> getIzin(Pegawai pegawai, Date tanggalAwal, Date tanggalAkhir) throws EntityNotExistException {
		return izinRepository.findByPegawaiAndKalendar_TanggalBetween(pegawai, tanggalAwal, tanggalAkhir);
	}

	@Override
	public List<Izin> getIzin(UnitKerja unitKerja, Date tanggalAwal, Date tanggalAkhir) throws EntityNotExistException {
		return izinRepository.findByPegawai_UnitKerjaAndKalendar_TanggalBetween(unitKerja, tanggalAwal, tanggalAkhir);
	}

	@Override
	public List<Cuti> getCuti(Pegawai pegawai, Date tanggalAwal, Date tanggalAkhir) throws EntityNotExistException {
		return cutiRepository.findByPegawaiAndKalendar_TanggalBetween(pegawai, tanggalAwal, tanggalAkhir);
	}

	@Override
	public List<Cuti> getCuti(UnitKerja unitKerja, Date tanggalAwal, Date tanggalAkhir) throws EntityNotExistException {
		return cutiRepository.findByPegawai_UnitKerjaAndKalendar_TanggalBetween(unitKerja, tanggalAwal, tanggalAkhir);
	}

}
