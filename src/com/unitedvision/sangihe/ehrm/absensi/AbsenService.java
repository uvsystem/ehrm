package com.unitedvision.sangihe.ehrm.absensi;

import java.sql.Date;
import java.sql.Time;
import java.time.Month;
import java.util.List;

import com.unitedvision.sangihe.ehrm.simpeg.Pegawai;
import com.unitedvision.sangihe.ehrm.simpeg.UnitKerja;

public interface AbsenService {

	Absen simpan(Absen absen);
	
	Absen apelPagi(String nip, Time jam);
	Absen pengecekanSatu(String nip, Time jam);
	Absen pengecekanDua(String nip, Time jam);
	Absen apelSore(String nip, Time jam);
	
	Absen get(long idAbsen);
	
	List<Absen> get(Pegawai pegawai, Month bulan);
	List<Absen> get(Pegawai pegawai, Date tanggalAwal, Date tanggalAkhir);
	List<Absen> get(UnitKerja unitKerja, Date tanggalAwal, Date tanggalAkhir);
	
}
