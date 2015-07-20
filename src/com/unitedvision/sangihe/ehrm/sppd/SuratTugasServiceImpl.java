package com.unitedvision.sangihe.ehrm.sppd;

import java.sql.Date;
import java.time.Month;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.unitedvision.sangihe.ehrm.DateUtil;
import com.unitedvision.sangihe.ehrm.simpeg.Pegawai;
import com.unitedvision.sangihe.ehrm.simpeg.repository.PegawaiRepository;
import com.unitedvision.sangihe.ehrm.sppd.SuratTugas.Status;
import com.unitedvision.sangihe.ehrm.sppd.repository.SuratTugasRepository;

@Service
@Transactional(readOnly = true)
public class SuratTugasServiceImpl implements SuratTugasService {

	@Autowired
	private SuratTugasRepository suratTugasRepository;
	@Autowired
	private PegawaiRepository pegawaiRepository;
	
	@Override
	@Transactional(readOnly = false)
	public SuratTugas simpan(SuratTugas suratTugas) {
		suratTugas.setStatus(Status.PENDING);
		
		return suratTugasRepository.save(suratTugas);
	}

	@Override
	@Transactional(readOnly = false)
	public SuratTugas simpan(SuratTugas suratTugas, List<Pegawai> daftarPegawai) {
		suratTugas.addPemegangTugas(daftarPegawai);
		suratTugas.setStatus(Status.PENDING);
		
		return suratTugasRepository.save(suratTugas);
	}

	@Override
	public SuratTugas simpanWithNip(SuratTugas suratTugas, List<String> daftarPegawai) {
		List<Pegawai> daftarPemegangTugas = pegawaiRepository.findByNipIn(daftarPegawai);
		
		return simpan(suratTugas, daftarPemegangTugas);
	}
	
	@Override
	@Transactional(readOnly = false)
	public SuratTugas izinkanPengajuan(SuratTugas suratTugas) {
		suratTugas.setStatus(Status.DITERIMA);
		
		return suratTugasRepository.save(suratTugas);
	}

	@Override
	@Transactional(readOnly = false)
	public SuratTugas izinkanPengajuan(String nomor) {
		SuratTugas suratTugas = get(nomor);
		
		return izinkanPengajuan(suratTugas);
	}
	
	@Override
	@Transactional(readOnly = false)
	public SuratTugas tolakPengajuan(SuratTugas suratTugas) {
		suratTugas.setStatus(Status.DITOLAK);
		
		return suratTugasRepository.save(suratTugas);
	}

	@Override
	@Transactional(readOnly = false)
	public SuratTugas tolakPengajuan(String nomor) {
		SuratTugas suratTugas = get(nomor);
		
		return tolakPengajuan(suratTugas);
	}
	
	@Override
	@Transactional(readOnly = false)
	public SuratTugas tambahPegawai(SuratTugas suratTugas, Pegawai pegawai) {
		suratTugas.addPemegangTugas(pegawai);
		
		return suratTugasRepository.save(suratTugas);
	}

	@Override
	@Transactional(readOnly = false)
	public SuratTugas tambahPegawai(long idSuratTugas, String nip) {
		SuratTugas suratTugas = suratTugasRepository.findOne(idSuratTugas);
		Pegawai pegawai = pegawaiRepository.findByNip(nip);
		
		suratTugas.addPemegangTugas(pegawai);
		
		return suratTugasRepository.save(suratTugas);
	}

	@Override
	@Transactional(readOnly = false)
	public void hapus(SuratTugas suratTugas) {
		suratTugasRepository.delete(suratTugas);
	}

	@Override
	@Transactional(readOnly = false)
	public void hapus(long idSuratTugas) {
		suratTugasRepository.delete(idSuratTugas);
	}

	@Override
	public SuratTugas get(long idSuratTugas) {
		return suratTugasRepository.findOne(idSuratTugas);
	}

	@Override
	public SuratTugas get(String nomorSuratTugas) {
		return suratTugasRepository.findByNomor(nomorSuratTugas);
	}

	@Override
	public List<SuratTugas> getByPegawai(Pegawai pegawai) {
		return suratTugasRepository.findByPegawai(pegawai);
	}

	@Override
	public List<SuratTugas> getByPegawai(String nip) {
		Pegawai pegawai = pegawaiRepository.findByNip(nip);
		
		return getByPegawai(pegawai);
	}
	
	@Override
	public List<SuratTugas> getByStatus(Status status) {
		Date sekarang = DateUtil.getDate();

		int tahun = DateUtil.getYear(sekarang) - 2;
		Date duaTahunLalu = DateUtil.getDate(tahun, Month.JANUARY, 1);

		return suratTugasRepository.findByStatusAndTanggalBetween(status, duaTahunLalu, sekarang);
	}

}
