package com.unitedvision.sangihe.ehrm.absensi.repository;

import java.sql.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.unitedvision.sangihe.ehrm.absensi.TugasLuar;
import com.unitedvision.sangihe.ehrm.simpeg.Pegawai;
import com.unitedvision.sangihe.ehrm.simpeg.UnitKerja;
import com.unitedvision.sangihe.ehrm.sppd.Sppd;

public interface TugasLuarRepository extends JpaRepository<TugasLuar, Long> {

	List<TugasLuar> findByPegawaiAndKalendar_TanggalBetween(Pegawai pegawai, Date tanggalAwal, Date tanggalAkhir);

	List<TugasLuar> findByPegawai_UnitKerjaAndKalendar_TanggalBetween(UnitKerja unitKerja, Date tanggalAwal, Date tanggalAkhir);
	
	long countBySppd(Sppd sppd);

}
