package com.unitedvision.sangihe.ehrm.simpeg;

import java.util.List;

public interface JabatanService {

	Jabatan simpan(Long idUnitKerja, Jabatan jabatan);
	Jabatan simpan(Jabatan jabatan);
	
	void hapus(Jabatan jabatan);
	void hapus(Long idJabatan);
	
	Jabatan get(Long idJabatan);
	
	List<Jabatan> getByUnitKerja(UnitKerja unitKerja);
	List<Jabatan> getByUnitKerja(Long idUnitKerja);
	List<Jabatan> cari(String keyword);

}
