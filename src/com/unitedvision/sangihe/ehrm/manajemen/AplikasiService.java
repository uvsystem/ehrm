package com.unitedvision.sangihe.ehrm.manajemen;

import java.util.List;

import com.unitedvision.sangihe.ehrm.EntityNotExistException;
import com.unitedvision.sangihe.ehrm.manajemen.Operator.Role;
import com.unitedvision.sangihe.ehrm.simpeg.Pegawai;

public interface AplikasiService {

	Aplikasi simpan(Aplikasi aplikasi);

	void hapus(Aplikasi aplikasi);
	void hapus(long idAplikasi);

	Aplikasi get(long idAplikasi) throws EntityNotExistException;

	List<Aplikasi> get(String kode) throws EntityNotExistException;
	List<Aplikasi> get() throws EntityNotExistException;

	Aplikasi tambahAdmin(Pegawai pegawai, Aplikasi aplikasi);
	Aplikasi tambahAdmin(String nip, String kodeAplikasi) throws EntityNotExistException;

	Aplikasi tambahOperator(Pegawai pegawai, Aplikasi aplikasi);
	Aplikasi tambahOperator(String nip, String kodeAplikasi) throws EntityNotExistException;
	
	Operator promosi(long idOperator, Role role);
	
	void hapus(Operator operator);

	List<Operator> get(Aplikasi aplikasi) throws EntityNotExistException;
	List<Operator> getOperator(String kode) throws EntityNotExistException;

}
