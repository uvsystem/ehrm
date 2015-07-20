package com.unitedvision.sangihe.ehrm.simpeg;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.unitedvision.sangihe.ehrm.simpeg.repository.SubUnitKerjaRepository;
import com.unitedvision.sangihe.ehrm.simpeg.repository.UnitKerjaRepository;

@Service
@Transactional(readOnly = true)
public class UnitKerjaServiceImpl implements UnitKerjaService {

	@Autowired
	private UnitKerjaRepository unitKerjaRepository;
	@Autowired
	private SubUnitKerjaRepository subUnitKerjaRepository;
	
	@Override
	@Transactional(readOnly = false)
	public UnitKerja simpan(UnitKerja unitKerja) {
		return unitKerjaRepository.save(unitKerja);
	}

	@Override
	@Transactional(readOnly = false)
	public UnitKerja tambahSubUnit(long idUnitKerja, UnitKerja unitKerja) {
		SubUnitKerja subUnitKerja;

		if (unitKerja instanceof SubUnitKerja) {
			subUnitKerja = (SubUnitKerja)unitKerja;
		} else {
			subUnitKerja = new SubUnitKerja(unitKerja);
		}
		
		UnitKerja parent = unitKerjaRepository.findOne(idUnitKerja);
		subUnitKerja.setUnitKerja(parent);
		
		return unitKerjaRepository.save(subUnitKerja);
	}

	@Override
	@Transactional(readOnly = false)
	public void hapus(UnitKerja unitKerja) {
		unitKerjaRepository.delete(unitKerja);
	}

	@Override
	@Transactional(readOnly = false)
	public void hapus(Long idUnitKerja) {
		unitKerjaRepository.delete(idUnitKerja);
	}

	@Override
	public UnitKerja get(Long idUnitkerja) {
		return unitKerjaRepository.findOne(idUnitkerja);
	}

	@Override
	public List<SubUnitKerja> getSubUnitKerja(UnitKerja unitKerja) {
		return subUnitKerjaRepository.findByUnitKerja(unitKerja);
	}
	
	@Override
	public List<SubUnitKerja> getSubUnitKerja(Long id) {
		UnitKerja unitKerja = unitKerjaRepository.findOne(id);
		
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
