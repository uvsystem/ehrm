package com.unitedvision.sangihe.ehrm.simpeg;

import java.util.List;

public interface UnitKerjaService {

	UnitKerja simpan(UnitKerja unitKerja);
	UnitKerja tambahSubUnit(long idUnitKerja, UnitKerja unitKerja);
	
	void hapus(UnitKerja unitKerja);
	void hapus(String kode);
	
	UnitKerja get(Long idUnitKerja);
	UnitKerja get(String kode);
	
	List<SubUnitKerja> getSubUnitKerja(UnitKerja unitKerja);
	List<SubUnitKerja> getSubUnitKerja(Long id);
	
	List<UnitKerja> cari(String keyword);
	
	List<UnitKerja> get();
	
}
