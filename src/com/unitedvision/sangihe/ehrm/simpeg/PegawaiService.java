package com.unitedvision.sangihe.ehrm.simpeg;

import java.sql.Date;
import java.util.List;

import com.unitedvision.sangihe.ehrm.EntityNotExistException;

public interface PegawaiService {
	
	Pegawai simpan(Pegawai pegawai);

	Pegawai mutasi(Pegawai pegawai, UnitKerja unitKerja);
	Pegawai mutasi(String nip, long idUnitKerja);

	Pegawai promosi(Pegawai pegawai, Pangkat pangkat, Date tanggalPromosi, Date tanggalSelesai, String nomorSk);
	Pegawai promosi(String nip, Pangkat pangkat, Date tanggalPromosi, Date tanggalSelesai, String nomorSk);
	
	Pegawai promosi(Pegawai pegawai, Jabatan jabatan, Date tanggalPromosi, Date tanggalSelesai, String nomorSk);
	Pegawai promosi(String nip, Jabatan jabatan, Date tanggalPromosi, Date tanggalSelesai, String nomorSk);
	
	void hapus(Pegawai pegawai);
	void hapus(String nip);

	Pegawai getByNip(String nip) throws EntityNotExistException;
	
	List<Pegawai> get(UnitKerja unitKerja) throws EntityNotExistException;
	List<Pegawai> get(Pangkat pangkat) throws EntityNotExistException;
	List<Pegawai> get(Eselon eselon) throws EntityNotExistException;

	List<RiwayatPangkat> getRiwayatPangkat(Pegawai pegawai) throws EntityNotExistException;
	List<RiwayatPangkat> getRiwayatPangkat(String nip) throws EntityNotExistException;

	List<RiwayatPangkat> getRiwayatJabatan(Pegawai pegawai) throws EntityNotExistException;
	List<RiwayatPangkat> getRiwayatJabatan(String nip) throws EntityNotExistException;
	
}
