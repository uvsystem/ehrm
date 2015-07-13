package com.unitedvision.sangihe.ehrm.absensi;

import java.sql.Date;
import java.time.Month;
import java.util.List;

public interface KalendarService {

	Kalendar tambah(Kalendar kalendar);
	
	Kalendar tambah(Date tanggal);
	
	void hapus(Kalendar kalendar);
	
	void hapus(Date tanggal);

	Kalendar get(Date tanggal);
	
	List<Kalendar> get(Month bulan, int tahun);
	
	List<Kalendar> get(Date dateAwal, Date dateAkhir);

}
