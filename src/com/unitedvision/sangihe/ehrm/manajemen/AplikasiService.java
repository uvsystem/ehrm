package com.unitedvision.sangihe.ehrm.manajemen;

import java.util.List;

import com.unitedvision.sangihe.ehrm.manajemen.Operator.Role;
import com.unitedvision.sangihe.ehrm.simpeg.Pegawai;

public interface AplikasiService {

	Aplikasi simpan(Aplikasi aplikasi);

	void hapus(Aplikasi aplikasi);
	void hapus(long idAplikasi);

	Aplikasi get(long idAplikasi);
	Aplikasi getByKode(String kode);

	List<Aplikasi> get(String kode);
	List<Aplikasi> get();

	Aplikasi tambahAdmin(Pegawai pegawai, Aplikasi aplikasi);
	Aplikasi tambahAdmin(String nip, String kodeAplikasi);

	List<Operator> getAdmin(String kode);

	Aplikasi tambahOperator(Pegawai pegawai, Aplikasi aplikasi);
	Aplikasi tambahOperator(String nip, String kodeAplikasi);

	List<Operator> getOperator(String kode);
	
	Operator promosi(long idOperator, Role role);
	
	void hapus(Operator operator);
	void hapusOperator(Long idOperator);

	List<Operator> get(Pegawai pegawai);
	List<Operator> getByPegawai(String nip);

}
