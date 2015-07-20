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

	Aplikasi tambahOperator(Pegawai pegawai, Aplikasi aplikasi);
	Aplikasi tambahOperator(String nip, String kodeAplikasi);
	
	Operator promosi(long idOperator, Role role);
	
	void hapus(Operator operator);

	List<Operator> get(Aplikasi aplikasi, Role role);
	List<Operator> getOperator(String kode);
	List<Operator> getAdmin(String kode);

}
