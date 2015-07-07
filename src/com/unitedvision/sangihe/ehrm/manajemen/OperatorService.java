package com.unitedvision.sangihe.ehrm.manajemen;

import java.util.List;

import com.unitedvision.sangihe.ehrm.EntityNotExistException;
import com.unitedvision.sangihe.ehrm.simpeg.Pegawai;

public interface OperatorService {

	Operator simpan(Operator operator);
	
	Operator makeAdmin(Pegawai pegawai, Aplikasi aplikasi);
	Operator makeAdmin(String nip, String kodeAplikasi);
	Operator makeOperator(Pegawai pegawai, Aplikasi aplikasi);
	Operator makeOperator(String nip, String kodeAplikasi);
	
	void hapus(Operator operator);
	void hapus(long idOperator);
	
	Operator get(long idOperator) throws EntityNotExistException;
	
	List<Operator> get(Aplikasi aplikasi) throws EntityNotExistException;
	List<Operator> get(Pegawai pegawai) throws EntityNotExistException;
	List<Operator> get() throws EntityNotExistException;

}
