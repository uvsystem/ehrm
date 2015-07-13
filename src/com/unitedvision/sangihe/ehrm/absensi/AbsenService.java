package com.unitedvision.sangihe.ehrm.absensi;

import java.sql.Date;
import java.sql.Time;
import java.util.List;

import com.unitedvision.sangihe.ehrm.EntityNotExistException;
import com.unitedvision.sangihe.ehrm.simpeg.Pegawai;
import com.unitedvision.sangihe.ehrm.simpeg.UnitKerja;

public interface AbsenService {
	
	/**
	 * Isi absen pagi.
	 * @param nip Nomor Induk Pegawai
	 * @param tanggal tanggal absen
	 * @param jam Waktu isi absen. Jika null, akan digunakan waktu server.
	 * @return
	 * @throws AbsenException
	 * @throws EntityNotExistException 
	 */
	Hadir apelPagi(String nip, Date tanggal, Time jam) throws AbsenException, EntityNotExistException;

	/**
	 * Isi absen pengecekan pertama.
	 * @param nip Nomor Induk Pegawai
	 * @param tanggal tanggal absen
	 * @param jam Waktu isi absen. Jika null, akan digunakan waktu server.
	 * @return
	 * @throws AbsenException 
	 * @throws EntityNotExistException 
	 */
	Hadir pengecekanSatu(String nip, Date tanggal, Time jam) throws AbsenException, EntityNotExistException;

	/**
	 * Isi absen pengecekan kedua.
	 * @param nip Nomor Induk Pegawai
	 * @param tanggal tanggal absen
	 * @param jam Waktu isi absen. Jika null, akan digunakan waktu server.
	 * @return
	 * @throws AbsenException 
	 * @throws EntityNotExistException 
	 */
	Hadir pengecekanDua(String nip, Date tanggal, Time jam) throws AbsenException, EntityNotExistException;

	/**
	 * Isi absen sore.
	 * @param nip Nomor Induk Pegawai
	 * @param tanggal tanggal absen
	 * @param jam Waktu isi absen. Jika null, akan digunakan waktu server.
	 * @return
	 * @throws AbsenException 
	 * @throws EntityNotExistException 
	 */
	Hadir apelSore(String nip, Date tanggal, Time jam) throws AbsenException, EntityNotExistException;

	TugasLuar tambahTugasLuar(String nip, Date tanggal, String nomorSppd) throws EntityNotExistException;
	Sakit tambahSakit(String nip, Date tanggal, String penyakit) throws EntityNotExistException;
	Izin tambahIzin(String nip, Date tanggal, String alasan) throws EntityNotExistException;
	Cuti tambahCuti(String nip, Date tanggal, String jenisCuti) throws EntityNotExistException;
	
	List<Hadir> getHadir(Pegawai pegawai, Date tanggalAwal, Date tanggalAkhir) throws EntityNotExistException;
	List<Hadir> getHadir(UnitKerja unitKerja, Date tanggalAwal, Date tanggalAkhir) throws EntityNotExistException;
	
	List<TugasLuar> getTugasLuar(Pegawai pegawai, Date tanggalAwal, Date tanggalAkhir) throws EntityNotExistException;
	List<TugasLuar> getTugasLuar(UnitKerja unitKerja, Date tanggalAwal, Date tanggalAkhir) throws EntityNotExistException;
	
	List<Sakit> getSakit(Pegawai pegawai, Date tanggalAwal, Date tanggalAkhir) throws EntityNotExistException;
	List<Sakit> getSakit(UnitKerja unitKerja, Date tanggalAwal, Date tanggalAkhir) throws EntityNotExistException;
	
	List<Izin> getIzin(Pegawai pegawai, Date tanggalAwal, Date tanggalAkhir) throws EntityNotExistException;
	List<Izin> getIzin(UnitKerja unitKerja, Date tanggalAwal, Date tanggalAkhir) throws EntityNotExistException;
	
	List<Cuti> getCuti(Pegawai pegawai, Date tanggalAwal, Date tanggalAkhir) throws EntityNotExistException;
	List<Cuti> getCuti(UnitKerja unitKerja, Date tanggalAwal, Date tanggalAkhir) throws EntityNotExistException;
	
}
