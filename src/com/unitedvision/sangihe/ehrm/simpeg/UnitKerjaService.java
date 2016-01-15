package com.unitedvision.sangihe.ehrm.simpeg;

import java.util.List;

public interface UnitKerjaService {

	UnitKerja simpan(UnitKerja unitKerja);
	UnitKerja tambahSubUnit(String kode, UnitKerja unitKerja);
	
	void hapus(UnitKerja unitKerja);
	void hapus(String kode);
	void hapus(Long id);
	
	UnitKerja get(Long idUnitKerja);
	UnitKerja get(String kode);
	
	List<UnitKerja> getSubUnitKerja(UnitKerja unitKerja);
	List<UnitKerja> getSubUnitKerja(String kode);
	List<UnitKerja> getSubUnitKerja(Long id);
	
	List<UnitKerja> cari(String keyword);
	
	List<UnitKerja> get();
	
}
