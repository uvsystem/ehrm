package com.unitedvision.sangihe.ehrm.sppd;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.unitedvision.sangihe.ehrm.EntityNotExistException;
import com.unitedvision.sangihe.ehrm.simpeg.Pegawai;
import com.unitedvision.sangihe.ehrm.simpeg.repository.PegawaiRepository;
import com.unitedvision.sangihe.ehrm.sppd.repository.SppdRepository;

@Service
@Transactional(readOnly = true)
public class SppdServiceImpl implements SppdService {

	@Autowired
	private SppdRepository sppdRepository;
	@Autowired
	private PegawaiRepository pegawaiRepository;
	
	@Override
	@Transactional(readOnly = false)
	public Sppd simpan(Sppd sppd) {
		return sppdRepository.save(sppd);
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
		sppd.setDaftarPengikut(daftarPengikut);
		
		return sppdRepository.save(sppd);
	}

	@Override
	@Transactional(readOnly = false)
	public void hapus(Sppd sppd) {
		sppdRepository.delete(sppd);
	}

	@Override
	@Transactional(readOnly = false)
	public void hapus(long idSppd) {
		sppdRepository.delete(idSppd);
	}

	@Override
	public Sppd get(long idSppd) {
		return sppdRepository.findOne(idSppd);
	}

	@Override
	public Sppd get(String nomorSppd) throws EntityNotExistException {
		return sppdRepository.findByNomor(nomorSppd);
	}

	@Override
	public List<Sppd> getByPegawai(Pegawai pegawai) throws EntityNotExistException {
		return sppdRepository.findByPemegangTugas_Pegawai(pegawai);
	}

	@Override
	public List<Sppd> getByPegawai(String nip) throws EntityNotExistException {
		Pegawai pegawai = pegawaiRepository.findByNip(nip);
		
		return getByPegawai(pegawai);
	}

}
