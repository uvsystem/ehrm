package com.unitedvision.sangihe.ehrm.absensi.repository;

import java.sql.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.unitedvision.sangihe.ehrm.absensi.Hadir;
import com.unitedvision.sangihe.ehrm.simpeg.Pegawai;
import com.unitedvision.sangihe.ehrm.simpeg.UnitKerja;

public interface HadirRepository extends JpaRepository<Hadir, Long> {

	Hadir findByPegawaiAndKalendar_Tanggal(Pegawai pegawai, Date tanggal);

	List<Hadir> findByPegawaiAndKalendar_TanggalBetween(Pegawai pegawai, Date tanggalAwal, Date tanggalAkhir);

	List<Hadir> findByPegawai_UnitKerjaAndKalendar_TanggalBetween(UnitKerja unitKerja, Date tanggalAwal, Date tanggalAkhir);

}
