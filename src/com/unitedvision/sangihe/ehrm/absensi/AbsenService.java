package com.unitedvision.sangihe.ehrm.absensi;

import java.sql.Date;
import java.sql.Time;
import java.util.List;

import com.unitedvision.sangihe.ehrm.simpeg.Pegawai;
import com.unitedvision.sangihe.ehrm.simpeg.UnitKerja;

public interface AbsenService {
	
	Hadir apelPagi(String nip, Time jam);
	Hadir pengecekanSatu(String nip, Time jam);
	Hadir pengecekanDua(String nip, Time jam);
	Hadir apelSore(String nip, Time jam);

	TugasLuar tambahTugasLuar(String nip, Date tanggal, String nomorSppd);
	Sakit tambahSakit(String nip, Date tanggal, String penyakit);
	Izin tambahIzin(String nip, Date tanggal, String alasan);
	Cuti tambahCuti(String nip, Date tanggal, String jenisCuti);
	
	Absen get(long idAbsen);
	
	List<Hadir> getHadir(Pegawai pegawai, Date tanggalAwal, Date tanggalAkhir);
	List<Hadir> getHadir(UnitKerja unitKerja, Date tanggalAwal, Date tanggalAkhir);
	
	List<TugasLuar> getTugasLuar(Pegawai pegawai, Date tanggalAwal, Date tanggalAkhir);
	List<TugasLuar> getTugasLuar(UnitKerja unitKerja, Date tanggalAwal, Date tanggalAkhir);
	
	List<Sakit> getSakit(Pegawai pegawai, Date tanggalAwal, Date tanggalAkhir);
	List<Sakit> getSakit(UnitKerja unitKerja, Date tanggalAwal, Date tanggalAkhir);
	
	List<Izin> getIzin(Pegawai pegawai, Date tanggalAwal, Date tanggalAkhir);
	List<Izin> getIzin(UnitKerja unitKerja, Date tanggalAwal, Date tanggalAkhir);
	
	List<Cuti> getCuti(Pegawai pegawai, Date tanggalAwal, Date tanggalAkhir);
	List<Cuti> getCuti(UnitKerja unitKerja, Date tanggalAwal, Date tanggalAkhir);
	
}
