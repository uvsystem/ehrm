package com.unitedvision.sangihe.ehrm.simpeg;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.unitedvision.sangihe.ehrm.EntityNotExistException;
import com.unitedvision.sangihe.ehrm.simpeg.repository.JabatanRepository;

@Service
@Transactional
public class JabatanServiceImpl implements JabatanService {

	@Autowired
	private JabatanRepository jabatanRepository;
	
	@Override
	public Jabatan simpan(Jabatan jabatan) {
		return jabatanRepository.save(jabatan);
	}

	@Override
	public void hapus(Jabatan jabatan) {
		jabatanRepository.delete(jabatan);
	}

	@Override
	public void hapus(long idJabatan) {
		jabatanRepository.delete(idJabatan);
	}

	@Override
	public Jabatan get(long idJabatan) {
		return jabatanRepository.findOne(idJabatan);
	}

	@Override
	public List<Jabatan> get(UnitKerja unitKerja) throws EntityNotExistException {
		return jabatanRepository.findByUnitKerja(unitKerja);
	}
}
