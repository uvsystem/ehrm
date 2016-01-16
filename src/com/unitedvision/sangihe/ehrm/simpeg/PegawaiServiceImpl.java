package com.unitedvision.sangihe.ehrm.simpeg;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.PersistenceException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.unitedvision.sangihe.ehrm.IdenticRelationshipException;
import com.unitedvision.sangihe.ehrm.NullCollectionException;
import com.unitedvision.sangihe.ehrm.duk.repository.PendudukRepository;
import com.unitedvision.sangihe.ehrm.simpeg.Riwayat.Detail;
import com.unitedvision.sangihe.ehrm.simpeg.repository.JabatanRepository;
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
	private JabatanRepository jabatanRepository;
	
	@Override
	@Transactional(readOnly = false)
	public Pegawai simpan(Pegawai pegawai) {
		pegawai = pegawaiRepository.save(pegawai);
		
		return pegawai;
	}
	
	@Override
	@Transactional(readOnly = false)
	public Pegawai simpan(Long idUnitKerja, Pegawai pegawai) {
		UnitKerja unitKerja = unitKerjaRepository.findOne(idUnitKerja);
		pegawai.setUnitKerja(unitKerja);

		return simpan(pegawai);
	}
	
	@Override
	@Transactional(readOnly = false)
	public void hapus(Pegawai pegawai) {
		pegawaiRepository.delete(pegawai);
	}

	@Override
	@Transactional(readOnly = false)
	public void hapus(String nip) {
		hapus(getByNip(nip));
	}

	@Override
	@Transactional(readOnly = false)
	public void hapus(Long id) {
		pegawaiRepository.delete(id);
	}

	@Override
	public Pegawai getByNip(String nip) {
		Pegawai pegawai = pegawaiRepository.findByNip(nip);
		
		try {
			pegawai.setListPangkat(riwayatPangkatRepository.findByPegawai(pegawai));
		} catch(PersistenceException ex) { }
		
		try {
			pegawai.setListJabatan(riwayatJabatanRepository.findByPegawai(pegawai));
		} catch(PersistenceException ex) { }
		
		return pegawai;
	}

	@Override
	public List<Pegawai> get(UnitKerja unitKerja) {
		return pegawaiRepository.findByUnitKerja(unitKerja);
	}

	@Override
	public List<Pegawai> getByUnitKerja(Long idUnitKerja) {
		UnitKerja unitKerja = unitKerjaRepository.findOne(idUnitKerja);

		return get(unitKerja);
	}

	@Override
	public List<Pegawai> get(Pangkat pangkat) {
		return pegawaiRepository.findByPangkat(pangkat);
	}

	@Override
	public List<Pegawai> get(Eselon eselon) {
		return pegawaiRepository.findByJabatan_Eselon(eselon);
	}

	@Override
	public List<Pegawai> get() {
		return pegawaiRepository.findAll();
	}
	
	@Override
	public List<Pegawai> cari(String keyword) {
		return pegawaiRepository.findByNipContainingOrPenduduk_NamaContaining(keyword);
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
	public Pegawai mutasi(String nip, long idUnitKerja) throws IdenticRelationshipException {
		Pegawai pegawai = getByNip(nip);
		UnitKerja unitKerja = unitKerjaRepository.findOne(idUnitKerja);
		
		return mutasi(pegawai, unitKerja);
	}

	@Override
	@Transactional(readOnly = false)
	public Pegawai mutasi(String nip, String kode) throws IdenticRelationshipException {
		Pegawai pegawai = pegawaiRepository.findByNip(nip);
		UnitKerja unitKerja = unitKerjaRepository.findBySingkatan(kode);
		
		return mutasi(pegawai, unitKerja);
	}
	
	@Override
	@Transactional(readOnly = false)
	public Pegawai promosi(Pegawai pegawai, Pangkat pangkat, Date tanggalPromosi, Date tanggalSelesai, String nomorSk) throws IdenticRelationshipException {
		List<RiwayatPangkat> list = new ArrayList<>();

		try {
			if (tanggalSelesai != null)
				throw new NoPangkatException();
			
			RiwayatPangkat pangkatTerakhir = pegawai.getPangkatTerakhir();
			if (pangkat.equals(pangkatTerakhir.getPangkat()) && tanggalPromosi.equals(pangkatTerakhir.getTanggalMulai()))
				throw new IdenticRelationshipException("Pangkat pegawai sudah sama");
			
			pangkatTerakhir.setTanggalSelesai(tanggalPromosi);

			list.add(pangkatTerakhir);
		} catch (NullCollectionException | NoPangkatException e) { }

		RiwayatPangkat riwayatPangkat = new RiwayatPangkat();
		riwayatPangkat.setPegawai(pegawai);
		riwayatPangkat.setPangkat(pangkat);
		riwayatPangkat.setTanggalMulai(tanggalPromosi);
		riwayatPangkat.setTanggalSelesai(tanggalSelesai);
		riwayatPangkat.setNomorSk(nomorSk);
		
		list.add(riwayatPangkat);
		
		riwayatPangkatRepository.save(list);
		
		return getByNip(pegawai.getNip());
	}

	@Override
	@Transactional(readOnly = false)
	public Pegawai promosi(String nip, Pangkat pangkat, Date tanggalPromosi, Date tanggalSelesai, String nomorSk) throws IdenticRelationshipException {
		Pegawai pegawai = getByNip(nip);
		
		return promosi(pegawai, pangkat, tanggalPromosi, tanggalSelesai, nomorSk);
	}
	
	@Override
	@Transactional(readOnly = false)
	public Pegawai promosi(String nip, Pangkat pangkat, Detail detail) throws IdenticRelationshipException {
		Pegawai pegawai = pegawaiRepository.findByNip(nip);
		
		return promosi(pegawai, pangkat, detail.getTanggalMulai(), detail.getTanggalSelesai(), detail.getNomorSk());
	}
	
	@Override
	public void hapusRiwayatPangkat(Long idRiwayatPangkat) {
		riwayatPangkatRepository.delete(idRiwayatPangkat);
	}
	
	@Override
	public void hapusRiwayatPangkat(String nip, Pangkat pangkat) {
		riwayatPangkatRepository.deleteByPegawai_NipAndPangkat(nip, pangkat);
	}

	@Override
	public List<RiwayatPangkat> getRiwayatPangkat(Pegawai pegawai) {
		return riwayatPangkatRepository.findByPegawai(pegawai);
	}

	@Override
	public List<RiwayatPangkat> getRiwayatPangkat(String nip) {
		return riwayatPangkatRepository.findByPegawai_Nip(nip);
	}

	@Override
	@Transactional(readOnly = false)
	public Pegawai promosi(Pegawai pegawai, Jabatan jabatan, Date tanggalPromosi, Date tanggalSelesai, String nomorSk) throws IdenticRelationshipException {
		List<RiwayatJabatan> list = new ArrayList<>();

		try {
			if (tanggalSelesai != null)
				throw new NoJabatanException();
			
			RiwayatJabatan jabatanTerakhir = pegawai.getJabatanTerakhir();
			if (jabatan.equals(pegawai.getJabatan()) && tanggalPromosi.equals(jabatanTerakhir.getTanggalMulai()))
				throw new IdenticRelationshipException("Jabatan pegawai sudah sama");

			jabatanTerakhir.setTanggalSelesai(tanggalPromosi);
			list.add(jabatanTerakhir);
			
			mutasi(pegawai, jabatan.getUnitKerja());
		} catch (NoJabatanException | NullCollectionException | IdenticRelationshipException e) { }
		
		RiwayatJabatan riwayatJabatan = new RiwayatJabatan();
		riwayatJabatan.setPegawai(pegawai);
		riwayatJabatan.setJabatan(jabatan);
		riwayatJabatan.setTanggalMulai(tanggalPromosi);
		riwayatJabatan.setTanggalSelesai(tanggalSelesai);
		riwayatJabatan.setNomorSk(nomorSk);

		list.add(riwayatJabatan);
		
		riwayatJabatanRepository.save(list);
		
		return getByNip(pegawai.getNip());
	}

	@Override
	@Transactional(readOnly = false)
	public Pegawai promosi(String nip, Jabatan jabatan, Date tanggalPromosi, Date tanggalSelesai, String nomorSk) throws IdenticRelationshipException {
		Pegawai pegawai = getByNip(nip);
		
		return promosi(pegawai, jabatan, tanggalPromosi, tanggalSelesai, nomorSk);
	}

	@Override
	@Transactional(readOnly = false)
	public Pegawai promosi(String nip, Long idJabatan, Detail detail) throws IdenticRelationshipException {
		Pegawai pegawai = pegawaiRepository.findByNip(nip);
		Jabatan jabatan = jabatanRepository.findOne(idJabatan);

		return promosi(pegawai, jabatan, detail.getTanggalMulai(), detail.getTanggalSelesai(), detail.getNomorSk());
	}
	
	@Override
	public void hapusRiwayatJabatan(Long idRiwayatJabatan) {
		riwayatJabatanRepository.delete(idRiwayatJabatan);
	}
	
	@Override
	public void hapusRiwayatJabatan(String nip, Long idJabatan) {
		riwayatJabatanRepository.deleteByPegawai_NipAndJabatan_Id(nip, idJabatan);
	}

	@Override
	public List<RiwayatJabatan> getRiwayatJabatan(Pegawai pegawai) {
		return riwayatJabatanRepository.findByPegawai(pegawai);
	}

	@Override
	public List<RiwayatJabatan> getRiwayatJabatan(String nip) {
		return riwayatJabatanRepository.findByPegawai_Nip(nip);
	}
	
	@Override
	public void updatePassword(Long id, String password) {
		pegawaiRepository.updatePassword(id, password);
	}
}
