package com.unitedvision.sangihe.ehrm.absensi.repository;

import java.sql.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.unitedvision.sangihe.ehrm.absensi.Kalendar;

public interface KalendarRepository extends JpaRepository<Kalendar, Date> {

	List<Kalendar> findByTanggalBetween(Date awal, Date akhir);

}
