package com.unitedvision.sangihe.ehrm.simpeg;

import java.util.List;

public interface UnitKerjaService {

	UnitKerja simpan(UnitKerja unitKerja);
	UnitKerja tambahSubUnit(long idUnitKerja, UnitKerja unitKerja);
	
	void hapus(UnitKerja unitKerja);
	void hapus(Long idUnitKerja);
	
	UnitKerja get(Long idUnitkerja);
	
	List<SubUnitKerja> getSubUnitKerja(UnitKerja unitKerja);
	List<SubUnitKerja> getSubUnitKerja(Long id);
	
	List<UnitKerja> cari(String keyword);
	
}
