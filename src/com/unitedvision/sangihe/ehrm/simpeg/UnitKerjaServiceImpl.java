package com.unitedvision.sangihe.ehrm.simpeg;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.unitedvision.sangihe.ehrm.simpeg.repository.UnitKerjaRepository;

@Service
@Transactional(readOnly = true)
public class UnitKerjaServiceImpl implements UnitKerjaService {

	@Autowired
	private UnitKerjaRepository unitKerjaRepository;
	
	@Override
	@Transactional(readOnly = false)
	public UnitKerja simpan(UnitKerja unitKerja) {
		return unitKerjaRepository.save(unitKerja);
	}

	@Override
	@Transactional(readOnly = false)
	public UnitKerja tambahSubUnit(String kode, UnitKerja subUnitKerja) {
		UnitKerja parent = get(kode);
		subUnitKerja.setParent(parent);
		
		return unitKerjaRepository.save(subUnitKerja);
	}

	@Override
	@Transactional(readOnly = false)
	public void hapus(UnitKerja unitKerja) {
		unitKerjaRepository.delete(unitKerja);
	}

	@Override
	@Transactional(readOnly = false)
	public void hapus(String kode) {
		unitKerjaRepository.deleteBySingkatan(kode);
	}

	@Override
	@Transactional(readOnly = false)
	public void hapus(Long id) {
		unitKerjaRepository.delete(id);
	}

	@Override
	public UnitKerja get(Long idUnitkerja) {
		return unitKerjaRepository.findOne(idUnitkerja);
	}
	
	@Override
	public UnitKerja get(String kode) {
		return unitKerjaRepository.findBySingkatan(kode);
	}

	@Override
	public List<UnitKerja> getSubUnitKerja(UnitKerja unitKerja) {
		return unitKerjaRepository.findByParent(unitKerja);
	}
	
	@Override
	public List<UnitKerja> getSubUnitKerja(Long id) {
		UnitKerja unitKerja = unitKerjaRepository.findOne(id);
		
		return getSubUnitKerja(unitKerja);
	}
	
	@Override
	public List<UnitKerja> getSubUnitKerja(String kode) {
		UnitKerja unitKerja = unitKerjaRepository.findBySingkatan(kode);

		return getSubUnitKerja(unitKerja);
	}

	@Override
	public List<UnitKerja> cari(String keyword) {
		return unitKerjaRepository.findByNamaContainingOrSingkatanContaining(keyword);
	}
	
	@Override
	public List<UnitKerja> get() {
		return unitKerjaRepository.findAll();
	}
}
