package com.unitedvision.sangihe.ehrm.absensi;

import java.sql.Date;
import java.time.Month;
import java.util.List;

public interface KalendarService {

	Kalendar simpan(Kalendar kalendar);
	
	void hapus(Kalendar kalendar);

	Kalendar get(Date tanggal);
	
	List<Kalendar> get(Month bulan);
	
	List<Kalendar> get(Date dateAwal, Date dateAkhir);

}
