package com.unitedvision.sangihe.ehrm.simpeg;

import java.util.List;

public interface UnitKerjaService {

	UnitKerja simpan(UnitKerja unitKerja);
	UnitKerja tambahSubUnit(long idUnitKerja, UnitKerja unitKerja);
	
	void hapus(UnitKerja unitKerja);
	void hapus(long idUnitKerja);
	
	UnitKerja get(long idUnitkerja);
	UnitKerja get(String singkatan);
	
	List<SubUnitKerja> get(UnitKerja unitKerja);
	
}
