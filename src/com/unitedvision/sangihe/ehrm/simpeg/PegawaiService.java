package com.unitedvision.sangihe.ehrm.simpeg;

import java.sql.Date;
import java.util.List;

import com.unitedvision.sangihe.ehrm.IdenticRelationshipException;
import com.unitedvision.sangihe.ehrm.manajemen.Operator;

public interface PegawaiService {

	Pegawai simpan(Pegawai pegawai);
	Pegawai simpan(Long idUnitKerja, Pegawai pegawai);

	Pegawai mutasi(Pegawai pegawai, UnitKerja unitKerja) throws IdenticRelationshipException;
	Pegawai mutasi(String nip, long idUnitKerja) throws IdenticRelationshipException;

	Pegawai promosi(Pegawai pegawai, Pangkat pangkat, Date tanggalPromosi, Date tanggalSelesai, String nomorSk) throws IdenticRelationshipException;
	Pegawai promosi(String nip, Pangkat pangkat, Date tanggalPromosi, Date tanggalSelesai, String nomorSk) throws IdenticRelationshipException;

	Pegawai promosi(Pegawai pegawai, Jabatan jabatan, Date tanggalPromosi, Date tanggalSelesai, String nomorSk) throws IdenticRelationshipException;
	Pegawai promosi(String nip, Jabatan jabatan, Date tanggalPromosi, Date tanggalSelesai, String nomorSk) throws IdenticRelationshipException;

	void hapus(Pegawai pegawai);
	void hapus(String nip);

	Pegawai getByNip(String nip);

	List<Pegawai> getByUnitKerja(Long idUnitKerja);
	List<Pegawai> get(UnitKerja unitKerja);
	List<Pegawai> get(Pangkat pangkat);
	List<Pegawai> get(Eselon eselon);

	List<Pegawai> cari(String keyword);

	List<RiwayatPangkat> getRiwayatPangkat(Pegawai pegawai);
	List<RiwayatPangkat> getRiwayatPangkat(String nip);

	List<RiwayatJabatan> getRiwayatJabatan(Pegawai pegawai);
	List<RiwayatJabatan> getRiwayatJabatan(String nip);

	List<Operator> get(Pegawai pegawai);
	List<Operator> get(String nip);

}
