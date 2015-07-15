package com.unitedvision.sangihe.ehrm.absensi.repository;

import java.sql.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.unitedvision.sangihe.ehrm.absensi.Sakit;
import com.unitedvision.sangihe.ehrm.simpeg.Pegawai;
import com.unitedvision.sangihe.ehrm.simpeg.UnitKerja;

public interface SakitRepository extends JpaRepository<Sakit, Long> {

	List<Sakit> findByPegawaiAndKalendar_TanggalBetween(Pegawai pegawai, Date tanggalAwal, Date tanggalAkhir);

	List<Sakit> findByPegawai_UnitKerjaAndKalendar_TanggalBetween(UnitKerja unitKerja, Date tanggalAwal, Date tanggalAkhir);

}
