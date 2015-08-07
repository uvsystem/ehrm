package com.unitedvision.sangihe.ehrm.absensi;

import java.sql.Date;
import java.sql.Time;
import java.util.List;

import com.unitedvision.sangihe.ehrm.ApplicationException;
import com.unitedvision.sangihe.ehrm.simpeg.Pegawai;
import com.unitedvision.sangihe.ehrm.simpeg.UnitKerja;
import com.unitedvision.sangihe.ehrm.sppd.Sppd;

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
	Hadir apelPagi(String nip, Date tanggal, Time jam) throws AbsenException;

	List<Hadir> apelPagi(List<Absen.Detail> daftarAbsen) throws AbsenException;

	/**
	 * Isi absen pengecekan pertama.
	 * @param nip Nomor Induk Pegawai
	 * @param tanggal tanggal absen
	 * @param jam Waktu isi absen. Jika null, akan digunakan waktu server.
	 * @return
	 * @throws AbsenException 
	 * @throws EntityNotExistException 
	 */
	Hadir pengecekanSatu(String nip, Date tanggal, Time jam) throws AbsenException;

	List<Hadir> pengecekanSatu(List<Absen.Detail> daftarAbsen) throws AbsenException;

	/**
	 * Isi absen pengecekan kedua.
	 * @param nip Nomor Induk Pegawai
	 * @param tanggal tanggal absen
	 * @param jam Waktu isi absen. Jika null, akan digunakan waktu server.
	 * @return
	 * @throws AbsenException 
	 * @throws EntityNotExistException 
	 */
	Hadir pengecekanDua(String nip, Date tanggal, Time jam) throws AbsenException;

	List<Hadir> pengecekanDua(List<Absen.Detail> daftarAbsen) throws AbsenException;

	/**
	 * Isi absen sore.
	 * @param nip Nomor Induk Pegawai
	 * @param tanggal tanggal absen
	 * @param jam Waktu isi absen. Jika null, akan digunakan waktu server.
	 * @return
	 * @throws AbsenException 
	 * @throws EntityNotExistException 
	 */
	Hadir apelSore(String nip, Date tanggal, Time jam) throws AbsenException;

	List<Hadir> apelSore(List<Absen.Detail> daftarAbsen) throws AbsenException;

	TugasLuar tambahTugasLuar(String nip, Date tanggal, String nomorSppd);
	List<TugasLuar> tambahTugasLuar(Sppd sppd);

	Hadir hadir(String nip, Date date, Hadir.Detail detail) throws AbsenException;
	Sakit tambahSakit(String nip, Date tanggal, String penyakit);
	Izin tambahIzin(String nip, Date tanggal, String alasan);
	Cuti tambahCuti(String nip, Date tanggal, String jenisCuti);
	
	List<Hadir> getHadir(Pegawai pegawai, Date tanggalAwal, Date tanggalAkhir);
	List<Hadir> getHadir(String nip, Date tanggalAwal, Date tanggalAkhir);
	List<Hadir> getHadir(UnitKerja unitKerja, Date tanggalAwal, Date tanggalAkhir);
	List<Hadir> getHadir(Long idUnitKerja, Date tanggalAwal, Date tanggalAkhir);
	
	List<TugasLuar> getTugasLuar(Pegawai pegawai, Date tanggalAwal, Date tanggalAkhir);
	List<TugasLuar> getTugasLuar(String nip, Date tanggalAwal, Date tanggalAkhir);
	List<TugasLuar> getTugasLuar(UnitKerja unitKerja, Date tanggalAwal, Date tanggalAkhir);
	List<TugasLuar> getTugasLuar(Long idUnitKerja, Date tanggalAwal, Date tanggalAkhir);
	
	List<Sakit> getSakit(Pegawai pegawai, Date tanggalAwal, Date tanggalAkhir);
	List<Sakit> getSakit(String nip, Date tanggalAwal, Date tanggalAkhir);
	List<Sakit> getSakit(UnitKerja unitKerja, Date tanggalAwal, Date tanggalAkhir);
	List<Sakit> getSakit(Long idUnitKerja, Date tanggalAwal, Date tanggalAkhir);
	
	List<Izin> getIzin(Pegawai pegawai, Date tanggalAwal, Date tanggalAkhir);
	List<Izin> getIzin(String nip, Date tanggalAwal, Date tanggalAkhir);
	List<Izin> getIzin(UnitKerja unitKerja, Date tanggalAwal, Date tanggalAkhir);
	List<Izin> getIzin(Long idUnitKerja, Date tanggalAwal, Date tanggalAkhir);
	
	List<Cuti> getCuti(Pegawai pegawai, Date tanggalAwal, Date tanggalAkhir);
	List<Cuti> getCuti(String nip, Date tanggalAwal, Date tanggalAkhir);
	List<Cuti> getCuti(UnitKerja unitKerja, Date tanggalAwal, Date tanggalAkhir);
	List<Cuti> getCuti(Long idUnitKerja, Date tanggalAwal, Date tanggalAkhir);

	List<Absen> find(String kode, Date date);

	void hapus(Long id, String status) throws ApplicationException;

	List<Absen> cari(String keyword);

	List<RekapAbsen> rekapByUnitKerja(String kode, Date tanggalAwal, Date tanggalAkhir);
	List<RekapAbsen> rekap(Date tanggalAwal, Date tanggalAkhir);

}
