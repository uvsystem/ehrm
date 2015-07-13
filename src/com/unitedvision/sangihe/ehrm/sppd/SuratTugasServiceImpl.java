package com.unitedvision.sangihe.ehrm.sppd;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.unitedvision.sangihe.ehrm.EntityNotExistException;
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
	@Transactional(readOnly = false)
	public SuratTugas izinkanPengajuan(SuratTugas suratTugas) {
		suratTugas.setStatus(Status.DITERIMA);
		
		return suratTugasRepository.save(suratTugas);
	}

	@Override
	@Transactional(readOnly = false)
	public SuratTugas tolakPengajuan(SuratTugas suratTugas) {
		suratTugas.setStatus(Status.DITOLAK);
		
		return suratTugasRepository.save(suratTugas);
	}

	@Override
	@Transactional(readOnly = false)
	public SuratTugas tambahPegawai(SuratTugas suratTugas, Pegawai pegawai) {
		suratTugas.addPemegangTugas(pegawai);
		
		return suratTugasRepository.save(suratTugas);
	}

	@Override
	@Transactional(readOnly = false)
	public SuratTugas tambahPegawai(long idSuratTugas, String nip) throws EntityNotExistException {
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
	public SuratTugas get(String nomorSuratTugas) throws EntityNotExistException {
		return suratTugasRepository.findByNomor(nomorSuratTugas);
	}

	@Override
	public List<SuratTugas> getByPegawai(Pegawai pegawai) throws EntityNotExistException {
		return suratTugasRepository.findByPegawai(pegawai);
	}

	@Override
	public List<SuratTugas> getByPegawai(String nip) throws EntityNotExistException {
		Pegawai pegawai = pegawaiRepository.findByNip(nip);
		
		return getByPegawai(pegawai);
	}

}
