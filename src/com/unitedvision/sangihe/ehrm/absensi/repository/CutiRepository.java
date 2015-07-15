package com.unitedvision.sangihe.ehrm.absensi.repository;

import java.sql.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.unitedvision.sangihe.ehrm.absensi.Cuti;
import com.unitedvision.sangihe.ehrm.simpeg.Pegawai;
import com.unitedvision.sangihe.ehrm.simpeg.UnitKerja;

public interface CutiRepository extends JpaRepository<Cuti, Long> {

	List<Cuti> findByPegawaiAndKalendar_TanggalBetween(Pegawai pegawai, Date tanggalAwal, Date tanggalAkhir);

	List<Cuti> findByPegawai_UnitKerjaAndKalendar_TanggalBetween(UnitKerja unitKerja, Date tanggalAwal, Date tanggalAkhir);

}
