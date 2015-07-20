package com.unitedvision.sangihe.ehrm.sppd;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.unitedvision.sangihe.ehrm.absensi.AbsenService;
import com.unitedvision.sangihe.ehrm.simpeg.Pegawai;
import com.unitedvision.sangihe.ehrm.simpeg.repository.PegawaiRepository;
import com.unitedvision.sangihe.ehrm.sppd.Sppd.Message;
import com.unitedvision.sangihe.ehrm.sppd.repository.PemegangTugasRepository;
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
	
	@Override
	@Transactional(readOnly = false)
	public Sppd simpan(Sppd sppd) {
		sppd = sppdRepository.save(sppd);
		
		absenService.tambahTugasLuar(sppd);
		
		return sppd;
	}

	@Override
	public Sppd simpan(String nip, String nomor, Message message) {
		PemegangTugas pemegangTugas = pemegangTugasRepository.findByPegawai_NipAndSuratTugas_Nomor(nip, nomor);
		
		return simpan(new Sppd(message, pemegangTugas));
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
	public void hapus(long idSppd) {
		sppdRepository.delete(idSppd);
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

}
