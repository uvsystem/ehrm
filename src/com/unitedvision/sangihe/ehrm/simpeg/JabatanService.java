package com.unitedvision.sangihe.ehrm.simpeg;

import java.util.List;

import com.unitedvision.sangihe.ehrm.EntityNotExistException;

public interface JabatanService {

	Jabatan simpan(Jabatan jabatan);
	
	void hapus(Jabatan jabatan);
	void hapus(long idJabatan);
	
	Jabatan get(long idJabatan);
	
	List<Jabatan> get(UnitKerja unitKerja) throws EntityNotExistException;

}
