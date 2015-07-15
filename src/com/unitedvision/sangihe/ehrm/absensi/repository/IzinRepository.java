package com.unitedvision.sangihe.ehrm.absensi.repository;

import java.sql.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.unitedvision.sangihe.ehrm.absensi.Izin;
import com.unitedvision.sangihe.ehrm.simpeg.Pegawai;
import com.unitedvision.sangihe.ehrm.simpeg.UnitKerja;

public interface IzinRepository extends JpaRepository<Izin, Long> {

	List<Izin> findByPegawaiAndKalendar_TanggalBetween(Pegawai pegawai, Date tanggalAwal, Date tanggalAkhir);

	List<Izin> findByPegawai_UnitKerjaAndKalendar_TanggalBetween(UnitKerja unitKerja, Date tanggalAwal, Date tanggalAkhir);

}
