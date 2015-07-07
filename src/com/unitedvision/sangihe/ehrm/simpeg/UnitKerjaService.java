package com.unitedvision.sangihe.ehrm.simpeg;

import java.util.List;

import com.unitedvision.sangihe.ehrm.EntityNotExistException;

public interface UnitKerjaService {

	UnitKerja simpan(UnitKerja unitKerja);
	UnitKerja tambahSubUnit(long idUnitKerja, UnitKerja unitKerja) throws EntityNotExistException;
	
	void hapus(UnitKerja unitKerja);
	void hapus(long idUnitKerja);
	
	UnitKerja get(long idUnitkerja) throws EntityNotExistException;
	UnitKerja get(String singkatan) throws EntityNotExistException;
	
	List<UnitKerja> get(UnitKerja unitKerja) throws EntityNotExistException;
	
}
