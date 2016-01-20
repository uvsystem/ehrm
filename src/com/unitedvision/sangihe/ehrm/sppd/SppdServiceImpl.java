package com.unitedvision.sangihe.ehrm.sppd;

import java.sql.Date;
import java.time.Month;
import java.util.List;

import javax.persistence.PersistenceException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.unitedvision.sangihe.ehrm.DateUtil;
import com.unitedvision.sangihe.ehrm.absensi.AbsenService;
import com.unitedvision.sangihe.ehrm.simpeg.Pegawai;
import com.unitedvision.sangihe.ehrm.simpeg.repository.PegawaiRepository;
import com.unitedvision.sangihe.ehrm.sppd.Sppd.Message;
import com.unitedvision.sangihe.ehrm.sppd.repository.PemegangTugasRepository;
import com.unitedvision.sangihe.ehrm.sppd.repository.RekapSppdRepository;
import com.unitedvision.sangihe.ehrm.sppd.repository.SppdRepository;

@Service
@Transactional(readOnly = true)
public class SppdServiceImpl implements SppdService {

	@Autowired
	private AbsenService absenService;
	@Autowired
	private SppdRepository sppdRepository;
	@Autowired
	private PegawaiRepository pegawaiRepository;
	@Autowired
	private PemegangTugasRepository pemegangTugasRepository;
	@Autowired
	private RekapSppdRepository rekapSppdRepository;
	
	@Override
	@Transactional(readOnly = false)
	public Sppd simpan(Sppd sppd) {
		boolean isNew = (sppd.getId() == 0);
		sppd = sppdRepository.save(sppd);
		
		if (isNew)
			absenService.tambahTugasLuar(sppd);
		
		return sppd;
	}

	@Override
	@Transactional(readOnly = false)
	public Sppd simpan(String nip, String nomor, Message message) {
		PemegangTugas pemegangTugas;
		try {
			pemegangTugas = pemegangTugasRepository.findByPegawai_NipAndSuratTugas_Nomor(nip, nomor);
		} catch (PersistenceException e) {
			throw new PersistenceException(String.format("Pegawai dengan NIP: %s tidak terdaftar dalam SPT dengan nomor: %s", nip, nomor));
		}
		
		Sppd sppd = new Sppd(message, pemegangTugas);
		
		return simpan(sppd);
	}
	
	@Override
	@Transactional(readOnly = false)
	public Sppd tambahPengikut(Sppd sppd, Pengikut pengikut) {
		sppd.addPengikut(pengikut);
		
		return sppdRepository.save(sppd);
	}

	@Override
	@Transactional(readOnly = false)
	public Sppd tambahPengikut(long idSppd, Pengikut pengikut) {
		Sppd sppd = sppdRepository.findOne(idSppd);
		
		return tambahPengikut(sppd, pengikut);
	}

	@Override
	@Transactional(readOnly = false)
	public Sppd tambahPengikut(Sppd sppd, List<Pengikut> daftarPengikut) {
		for (Pengikut pengikut : daftarPengikut)
			sppd.addPengikut(pengikut);
		
		return sppdRepository.save(sppd);
	}
	
	@Override
	@Transactional(readOnly = false)
	public Sppd tambahPengikut(String nomor, Pengikut pengikut) {
		Sppd sppd = get(nomor);
		
		return tambahPengikut(sppd, pengikut);
	}

	@Override
	@Transactional(readOnly = false)
	public void hapus(Sppd sppd) {
		sppdRepository.delete(sppd);
	}

	@Override
	@Transactional(readOnly = false)
	public void hapus(Long id) {
		sppdRepository.delete(id);
	}	

	@Override
	public Sppd get(long idSppd) {
		return sppdRepository.findOne(idSppd);
	}

	@Override
	public Sppd get(String nomorSppd) {
		return sppdRepository.findByNomor(nomorSppd);
	}

	@Override
	public List<Sppd> getByPegawai(Pegawai pegawai) {
		return sppdRepository.findByPemegangTugas_Pegawai(pegawai);
	}

	@Override
	public List<Sppd> getByPegawai(String nip) {
		Pegawai pegawai = pegawaiRepository.findByNip(nip);
		
		return getByPegawai(pegawai);
	}

	@Override
	public List<Sppd> getByTanggal(Date awal, Date akhir) {
		return sppdRepository.findByTanggalBerangkatBetween(awal, akhir);
	}

	@Override
	public List<Sppd> getUnitKerja(String kode) {
		int year = DateUtil.getYear();
		Date awal = DateUtil.getFirstDate(year);
		Date akhir = DateUtil.getLastDate(year);

		return sppdRepository.findByPemegangTugas_Pegawai_UnitKerja_SingkatanAndTanggalBerangkatBetween(kode, awal, akhir);
	}
	
	@Override
	public List<Sppd> cari(String keyword) {
		return sppdRepository.findByPegawaiOrNomor(keyword);
	}

	@Override
	public List<RekapSppd> rekap(Integer tahun) {
		Date awal = DateUtil.getDate(tahun, Month.JANUARY, 1);
		Date akhir = DateUtil.getDate(tahun, Month.DECEMBER, 31);
		return rekapSppdRepository.rekapSppd(awal, akhir);
	}

	@Override
	public List<RekapSppd> rekap(Date awal, Date akhir) {
		return rekapSppdRepository.rekapSppd(awal, akhir);
	}

}
