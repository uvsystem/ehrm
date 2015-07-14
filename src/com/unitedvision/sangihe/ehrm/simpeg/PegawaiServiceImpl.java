package com.unitedvision.sangihe.ehrm.simpeg;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.unitedvision.sangihe.ehrm.EntityNotExistException;
import com.unitedvision.sangihe.ehrm.IdenticRelationshipException;
import com.unitedvision.sangihe.ehrm.NullCollectionException;
import com.unitedvision.sangihe.ehrm.duk.repository.PendudukRepository;
import com.unitedvision.sangihe.ehrm.manajemen.Operator;
import com.unitedvision.sangihe.ehrm.manajemen.repository.OperatorRepository;
import com.unitedvision.sangihe.ehrm.simpeg.repository.PegawaiRepository;
import com.unitedvision.sangihe.ehrm.simpeg.repository.RiwayatJabatanRepository;
import com.unitedvision.sangihe.ehrm.simpeg.repository.RiwayatPangkatRepository;
import com.unitedvision.sangihe.ehrm.simpeg.repository.UnitKerjaRepository;

@Service
@Transactional(readOnly = true)
public class PegawaiServiceImpl implements PegawaiService {

	@Autowired
	private PegawaiRepository pegawaiRepository;
	@Autowired
	private PendudukRepository pendudukRepository;
	@Autowired
	private UnitKerjaRepository unitKerjaRepository;
	@Autowired
	private RiwayatPangkatRepository riwayatPangkatRepository;
	@Autowired
	private RiwayatJabatanRepository riwayatJabatanRepository;
	@Autowired
	private OperatorRepository operatorRepository;
	
	@Override
	@Transactional(readOnly = false)
	public Pegawai simpan(Pegawai pegawai) {
		return pegawaiRepository.save(pegawai);
	}

	@Override
	@Transactional(readOnly = false)
	public Pegawai mutasi(Pegawai pegawai, UnitKerja unitKerja) throws IdenticRelationshipException {
		if (unitKerja.equals(pegawai.getUnitKerja()))
			throw new IdenticRelationshipException("Unit kerja pegawai sudah sama");
		
		pegawai.setUnitKerja(unitKerja);
		
		return pegawaiRepository.save(pegawai);
	}

	@Override
	@Transactional(readOnly = false)
	public Pegawai mutasi(String nip, long idUnitKerja) throws IdenticRelationshipException, EntityNotExistException {
		Pegawai pegawai = getByNip(nip);
		UnitKerja unitKerja = unitKerjaRepository.findOne(idUnitKerja);
		
		return mutasi(pegawai, unitKerja);
	}

	@Override
	@Transactional(readOnly = false)
	public Pegawai promosi(Pegawai pegawai, Pangkat pangkat, Date tanggalPromosi, Date tanggalSelesai, String nomorSk) throws IdenticRelationshipException {
		List<RiwayatPangkat> list = new ArrayList<>();
		RiwayatPangkat pangkatTerakhir;

		try {
			pangkatTerakhir = pegawai.getPangkatTerakhir();
			
			if (pangkat.equals(pangkatTerakhir.getPangkat()))
				throw new IdenticRelationshipException("Unit kerja pegawai sudah sama");
			
			pangkatTerakhir.setTanggalSelesai(tanggalPromosi);

			list.add(pangkatTerakhir);
		} catch (NullCollectionException e) { }

		RiwayatPangkat riwayatPangkat = new RiwayatPangkat();
		riwayatPangkat.setPegawai(pegawai);
		riwayatPangkat.setPangkat(pangkat);
		riwayatPangkat.setTanggalMulai(tanggalPromosi);
		riwayatPangkat.setTanggalSelesai(tanggalSelesai);
		riwayatPangkat.setNomorSk(nomorSk);
		
		list.add(riwayatPangkat);
		
		riwayatPangkatRepository.save(list);
		
		return pegawai;
	}

	@Override
	@Transactional(readOnly = false)
	public Pegawai promosi(String nip, Pangkat pangkat, Date tanggalPromosi, Date tanggalSelesai, String nomorSk) throws IdenticRelationshipException, EntityNotExistException {
		Pegawai pegawai = getByNip(nip);
		
		return promosi(pegawai, pangkat, tanggalPromosi, tanggalSelesai, nomorSk);
	}

	@Override
	@Transactional(readOnly = false)
	public Pegawai promosi(Pegawai pegawai, Jabatan jabatan, Date tanggalPromosi, Date tanggalSelesai, String nomorSk) throws IdenticRelationshipException {
		List<RiwayatJabatan> list = new ArrayList<>();
		RiwayatJabatan jabatanTerakhir;
		try {
			jabatanTerakhir = pegawai.getJabatanTerakhir();
			
			if (jabatan.equals(pegawai.getJabatan()))
				throw new IdenticRelationshipException("Unit kerja pegawai sudah sama");

			jabatanTerakhir.setTanggalSelesai(tanggalPromosi);
			
			list.add(jabatanTerakhir);
		} catch (NullCollectionException e) { }
		
		RiwayatJabatan riwayatJabatan = new RiwayatJabatan();
		riwayatJabatan.setPegawai(pegawai);
		riwayatJabatan.setJabatan(jabatan);
		riwayatJabatan.setTanggalMulai(tanggalPromosi);
		riwayatJabatan.setTanggalSelesai(tanggalSelesai);
		riwayatJabatan.setNomorSk(nomorSk);

		list.add(riwayatJabatan);
		
		riwayatJabatanRepository.save(list);
		
		return pegawai;
	}

	@Override
	@Transactional(readOnly = false)
	public Pegawai promosi(String nip, Jabatan jabatan, Date tanggalPromosi, Date tanggalSelesai, String nomorSk) throws IdenticRelationshipException, EntityNotExistException {
		Pegawai pegawai = getByNip(nip);
		
		return promosi(pegawai, jabatan, tanggalPromosi, tanggalSelesai, nomorSk);
	}

	@Override
	@Transactional(readOnly = false)
	public void hapus(Pegawai pegawai) {
		pegawaiRepository.delete(pegawai);
	}

	@Override
	@Transactional(readOnly = false)
	public void hapus(String nip) throws EntityNotExistException {
		Pegawai pegawai = getByNip(nip);

		hapus(pegawai);
	}

	@Override
	public Pegawai getByNip(String nip) throws EntityNotExistException {
		Pegawai pegawai = pegawaiRepository.findByNip(nip);
		
		List<RiwayatPangkat> daftarPangkat = riwayatPangkatRepository.findByPegawai(pegawai);
		List<RiwayatJabatan> daftarJabatan = riwayatJabatanRepository.findByPegawai(pegawai);

		pegawai.setDaftarPangkat(daftarPangkat);
		pegawai.setDaftarJabatan(daftarJabatan);
		
		return pegawai;
	}

	@Override
	public List<Pegawai> get(UnitKerja unitKerja) throws EntityNotExistException {
		return pegawaiRepository.findByUnitKerja(unitKerja);
	}

	@Override
	public List<Pegawai> get(Pangkat pangkat) throws EntityNotExistException {
		return pegawaiRepository.findByPangkat(pangkat);
	}

	@Override
	public List<Pegawai> get(Eselon eselon) throws EntityNotExistException {
		return pegawaiRepository.findByJabatan_Eselon(eselon);
	}

	@Override
	public List<RiwayatPangkat> getRiwayatPangkat(Pegawai pegawai) throws EntityNotExistException {
		return riwayatPangkatRepository.findByPegawai(pegawai);
	}

	@Override
	public List<RiwayatPangkat> getRiwayatPangkat(String nip) throws EntityNotExistException {
		Pegawai pegawai = getByNip(nip);
		
		return getRiwayatPangkat(pegawai);
	}

	@Override
	public List<RiwayatJabatan> getRiwayatJabatan(Pegawai pegawai) throws EntityNotExistException {
		return riwayatJabatanRepository.findByPegawai(pegawai);
	}

	@Override
	public List<RiwayatJabatan> getRiwayatJabatan(String nip) throws EntityNotExistException {
		Pegawai pegawai = getByNip(nip);
		
		return getRiwayatJabatan(pegawai);
	}

	@Override
	public List<Operator> get(Pegawai pegawai) throws EntityNotExistException {
		return operatorRepository.findByPegawai(pegawai);
	}

	@Override
	public List<Operator> get(String nip) throws EntityNotExistException {
		Pegawai pegawai = getByNip(nip);
		
		return pegawai.getDaftarOperator();
	}

}
