package com.unitedvision.sangihe.ehrm.simpeg;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.unitedvision.sangihe.ehrm.simpeg.repository.JabatanRepository;

@Service
@Transactional(readOnly = true)
public class JabatanServiceImpl implements JabatanService {

	@Autowired
	private JabatanRepository jabatanRepository;
	@Autowired
	private UnitKerjaService unitKerjaService;
	
	@Override
	@Transactional(readOnly = false)
	public Jabatan simpan(Jabatan jabatan) {
		return jabatanRepository.save(jabatan);
	}

	@Override
	@Transactional(readOnly = false)
	public Jabatan simpan(Long idUnitKerja, Jabatan jabatan) {
		UnitKerja unitKerja = unitKerjaService.get(idUnitKerja);
		jabatan.setUnitKerja(unitKerja);
		
		return simpan(jabatan);
	}
	
	@Override
	@Transactional(readOnly = false)
	public void hapus(Jabatan jabatan) {
		jabatanRepository.delete(jabatan);
	}

	@Override
	@Transactional(readOnly = false)
	public void hapus(Long idJabatan) {
		jabatanRepository.delete(idJabatan);
	}

	@Override
	public Jabatan get(Long idJabatan) {
		return jabatanRepository.findOne(idJabatan);
	}

	@Override
	public List<Jabatan> getByUnitKerja(UnitKerja unitKerja) {
		return jabatanRepository.findByUnitKerja(unitKerja);
	}
	
	@Override
	public List<Jabatan> getByUnitKerja(Long idUnitKerja) {
		UnitKerja unitKerja = unitKerjaService.get(idUnitKerja);
		
		return getByUnitKerja(unitKerja);
	}

	@Override
	public List<Jabatan> cari(String keyword) {
		return jabatanRepository.findByNamaContaining(keyword);
	}

}
