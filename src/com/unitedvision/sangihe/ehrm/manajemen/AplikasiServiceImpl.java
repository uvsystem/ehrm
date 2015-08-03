package com.unitedvision.sangihe.ehrm.manajemen;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.unitedvision.sangihe.ehrm.manajemen.Operator.Role;
import com.unitedvision.sangihe.ehrm.manajemen.repository.AplikasiRepository;
import com.unitedvision.sangihe.ehrm.manajemen.repository.OperatorRepository;
import com.unitedvision.sangihe.ehrm.simpeg.Pegawai;
import com.unitedvision.sangihe.ehrm.simpeg.repository.PegawaiRepository;

@Service
@Transactional(readOnly = true)
public class AplikasiServiceImpl implements AplikasiService {
	
	@Autowired
	private AplikasiRepository aplikasiRepository;
	@Autowired
	private OperatorRepository operatorRepository;
	@Autowired
	private PegawaiRepository pegawaiRepository;

	@Override
	@Transactional(readOnly = false)
	public Aplikasi simpan(Aplikasi aplikasi) {
		return aplikasiRepository.save(aplikasi);
	}

	@Override
	@Transactional(readOnly = false)
	public void hapus(Aplikasi aplikasi) {
		aplikasiRepository.delete(aplikasi);
	}

	@Override
	@Transactional(readOnly = false)
	public void hapus(long idAplikasi) {
		aplikasiRepository.delete(idAplikasi);
	}

	@Override
	public Aplikasi get(long idAplikasi) {
		return aplikasiRepository.findOne(idAplikasi);
	}

	@Override
	public List<Aplikasi> get(String kode) {
		return aplikasiRepository.findByKodeLike(kode);
	}
	
	@Override
	public Aplikasi getByKode(String kode) {
		return aplikasiRepository.findByKode(kode);
	}

	@Override
	public List<Aplikasi> get() {
		return aplikasiRepository.findAll();
	}

	@Override
	@Transactional(readOnly = false)
	public Aplikasi tambahAdmin(Pegawai pegawai, Aplikasi aplikasi) {
		aplikasi.addOperator(pegawai, Role.ADMIN);
		
		return aplikasiRepository.save(aplikasi);
	}

	@Override
	@Transactional(readOnly = false)
	public Aplikasi tambahAdmin(String nip, String kodeAplikasi) {
		Pegawai pegawai = pegawaiRepository.findByNip(nip);
		Aplikasi aplikasi = aplikasiRepository.findByKode(kodeAplikasi);
		
		return tambahAdmin(pegawai, aplikasi);
	}

	@Override
	@Transactional(readOnly = false)
	public Aplikasi tambahOperator(Pegawai pegawai, Aplikasi aplikasi) {
		aplikasi.addOperator(pegawai, Role.OPERATOR);
		
		return aplikasiRepository.save(aplikasi);
	}

	@Override
	@Transactional(readOnly = false)
	public Aplikasi tambahOperator(String nip, String kodeAplikasi) {
		Pegawai pegawai = pegawaiRepository.findByNip(nip);
		Aplikasi aplikasi = aplikasiRepository.findByKode(kodeAplikasi);
		
		return tambahOperator(pegawai, aplikasi);
	}
	
	@Override
	@Transactional(readOnly = false)
	public Operator promosi(long idOperator, Role role) {
		Operator operator= operatorRepository.findOne(idOperator);
		operator.setRole(role);
		
		return operatorRepository.save(operator);
	}

	@Override
	@Transactional(readOnly = false)
	public void hapus(Operator operator) {
		Aplikasi aplikasi = operator.getAplikasi();
		aplikasi.removeOperator(operator);

		operatorRepository.delete(operator);
	}

	@Override
	public List<Operator> getOperator(String kode) {
		return operatorRepository.findByAplikasi_KodeAndRole(kode, Operator.Role.OPERATOR);
	}

	@Override
	public List<Operator> getAdmin(String kode) {
		return operatorRepository.findByAplikasi_KodeAndRole(kode, Operator.Role.ADMIN);
	}

	@Override
	@Transactional(readOnly = false)
	public void hapusOperator(Long id) {
		Operator operator = operatorRepository.findOne(id);
		
		hapus(operator);
	}

	@Override
	public List<Operator> get(Pegawai pegawai) {
		return operatorRepository.findByPegawai(pegawai);
	}

	@Override
	public List<Operator> getByPegawai(String nip) {
		return operatorRepository.findByPegawai_Nip(nip);
	}

}
