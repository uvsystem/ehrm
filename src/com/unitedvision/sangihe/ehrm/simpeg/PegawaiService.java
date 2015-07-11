package com.unitedvision.sangihe.ehrm.simpeg;

import java.sql.Date;
import java.util.List;

import com.unitedvision.sangihe.ehrm.EntityNotExistException;
import com.unitedvision.sangihe.ehrm.IdenticRelationshipException;
import com.unitedvision.sangihe.ehrm.manajemen.Operator;

public interface PegawaiService {

	Pegawai simpan(Pegawai pegawai);

	Pegawai mutasi(Pegawai pegawai, UnitKerja unitKerja) throws IdenticRelationshipException;
	Pegawai mutasi(String nip, long idUnitKerja) throws IdenticRelationshipException, EntityNotExistException;

	Pegawai promosi(Pegawai pegawai, Pangkat pangkat, Date tanggalPromosi, Date tanggalSelesai, String nomorSk) throws IdenticRelationshipException;
	Pegawai promosi(String nip, Pangkat pangkat, Date tanggalPromosi, Date tanggalSelesai, String nomorSk) throws IdenticRelationshipException, EntityNotExistException;

	Pegawai promosi(Pegawai pegawai, Jabatan jabatan, Date tanggalPromosi, Date tanggalSelesai, String nomorSk) throws IdenticRelationshipException;
	Pegawai promosi(String nip, Jabatan jabatan, Date tanggalPromosi, Date tanggalSelesai, String nomorSk) throws IdenticRelationshipException, EntityNotExistException;

	void hapus(Pegawai pegawai);
	void hapus(String nip) throws EntityNotExistException;

	Pegawai getByNip(String nip) throws EntityNotExistException;

	List<Pegawai> get(UnitKerja unitKerja) throws EntityNotExistException;
	List<Pegawai> get(Pangkat pangkat) throws EntityNotExistException;
	List<Pegawai> get(Eselon eselon) throws EntityNotExistException;

	List<RiwayatPangkat> getRiwayatPangkat(Pegawai pegawai) throws EntityNotExistException;
	List<RiwayatPangkat> getRiwayatPangkat(String nip) throws EntityNotExistException;

	List<RiwayatJabatan> getRiwayatJabatan(Pegawai pegawai) throws EntityNotExistException;
	List<RiwayatJabatan> getRiwayatJabatan(String nip) throws EntityNotExistException;

	List<Operator> get(Pegawai pegawai) throws EntityNotExistException;
	List<Operator> get(String nip) throws EntityNotExistException;

}
