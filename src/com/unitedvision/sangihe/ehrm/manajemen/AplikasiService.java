package com.unitedvision.sangihe.ehrm.manajemen;

import java.util.List;

import com.unitedvision.sangihe.ehrm.EntityNotExistException;

public interface AplikasiService {

	Aplikasi simpan(Aplikasi aplikasi);
	
	void hapus(Aplikasi aplikasi);
	void hapus(long idAplikasi);

	Aplikasi get(long idAplikasi) throws EntityNotExistException;
	
	List<Aplikasi> get(String kode) throws EntityNotExistException;
	List<Aplikasi> get() throws EntityNotExistException;

}
