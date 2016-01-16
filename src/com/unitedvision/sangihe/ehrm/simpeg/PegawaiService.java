package com.unitedvision.sangihe.ehrm.simpeg;

import java.sql.Date;
import java.util.List;

import com.unitedvision.sangihe.ehrm.IdenticRelationshipException;
import com.unitedvision.sangihe.ehrm.simpeg.Riwayat.Detail;

public interface PegawaiService {

	Pegawai simpan(Pegawai pegawai);
	Pegawai simpan(Long idUnitKerja, Pegawai pegawai);

	void hapus(Pegawai pegawai);
	void hapus(String nip);
	void hapus(Long id);

	Pegawai getByNip(String nip);

	List<Pegawai> getByUnitKerja(Long idUnitKerja);
	List<Pegawai> get(UnitKerja unitKerja);
	List<Pegawai> get(Pangkat pangkat);
	List<Pegawai> get(Eselon eselon);

	List<Pegawai> get();

	List<Pegawai> cari(String keyword);

	Pegawai mutasi(Pegawai pegawai, UnitKerja unitKerja) throws IdenticRelationshipException;
	Pegawai mutasi(String nip, long idUnitKerja) throws IdenticRelationshipException;
	Pegawai mutasi(String nip, String kode) throws IdenticRelationshipException;

	Pegawai promosi(Pegawai pegawai, Pangkat pangkat, Date tanggalPromosi, Date tanggalSelesai, String nomorSk) throws IdenticRelationshipException;
	Pegawai promosi(String nip, Pangkat pangkat, Date tanggalPromosi, Date tanggalSelesai, String nomorSk) throws IdenticRelationshipException;
	Pegawai promosi(String nip, Pangkat pangkat, Detail detail) throws IdenticRelationshipException;

	List<RiwayatPangkat> getRiwayatPangkat(Pegawai pegawai);
	List<RiwayatPangkat> getRiwayatPangkat(String nip);

	void hapusRiwayatPangkat(String nip, Pangkat pangkat);
	void hapusRiwayatPangkat(Long idRiwayatPangkat);

	Pegawai promosi(Pegawai pegawai, Jabatan jabatan, Date tanggalPromosi, Date tanggalSelesai, String nomorSk) throws IdenticRelationshipException;
	Pegawai promosi(String nip, Jabatan jabatan, Date tanggalPromosi, Date tanggalSelesai, String nomorSk) throws IdenticRelationshipException;
	Pegawai promosi(String nip, Long idJabatan, Detail detail) throws IdenticRelationshipException;

	List<RiwayatJabatan> getRiwayatJabatan(Pegawai pegawai);
	List<RiwayatJabatan> getRiwayatJabatan(String nip);

	void hapusRiwayatJabatan(String nip, Long idJabatan);
	void hapusRiwayatJabatan(Long idRiwayatJabatan);

	void updatePassword(Long id, String password);

}
