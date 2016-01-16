package com.unitedvision.sangihe.ehrm.absensi.repository;

import java.sql.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.unitedvision.sangihe.ehrm.absensi.RekapAbsen;

public interface RekapAbsenRepository extends JpaRepository<RekapAbsen, String> {

	@Query(nativeQuery = true, value = "SELECT pgw.nip as nip, pdk.nama as nama, uk.nama as nama_unit_kerja" +
			", (SELECT COUNT(*) FROM kalendar WHERE tanggal between :tanggalAwal and :tanggalAkhir) as jumlah_hari " +
			", (SELECT COUNT(*) FROM absen in_a INNER JOIN pegawai in_p ON in_a.pegawai = in_p.id WHERE in_p.id = pgw.id and in_a.kalendar between :tanggalAwal and :tanggalAkhir and in_a.status = 'HADIR') as hadir " +
			", (SELECT COUNT(*) FROM absen in_a INNER JOIN pegawai in_p ON in_a.pegawai = in_p.id WHERE in_p.id = pgw.id and in_a.kalendar between :tanggalAwal and :tanggalAkhir and in_a.status ='HADIR' and (in_a.pagi > '07:30' Or in_a.pagi is null)) as terlambat " +
			", (SELECT COUNT(*) FROM absen in_a INNER JOIN pegawai in_p ON in_a.pegawai = in_p.id WHERE in_p.id = pgw.id and in_a.kalendar between :tanggalAwal and :tanggalAkhir and in_a.status = 'HADIR' and (in_a.sore < '16:00' Or in_a.sore is null)) as pulang " +
			", (SELECT COUNT(*) FROM absen in_a INNER JOIN pegawai in_p ON in_a.pegawai = in_p.id WHERE in_p.id = pgw.id and in_a.kalendar between :tanggalAwal and :tanggalAkhir and in_a.status = 'TUGAS_LUAR') as tugas_luar " +
			", (SELECT COUNT(*) FROM absen in_a INNER JOIN pegawai in_p ON in_a.pegawai = in_p.id WHERE in_p.id = pgw.id and in_a.kalendar between :tanggalAwal and :tanggalAkhir and in_a.status = 'SAKIT') as sakit " +
			", (SELECT COUNT(*) FROM absen in_a INNER JOIN pegawai in_p ON in_a.pegawai = in_p.id WHERE in_p.id = pgw.id and in_a.kalendar between :tanggalAwal and :tanggalAkhir and in_a.status = 'IZIN') as izin " +
			", (SELECT COUNT(*) FROM absen in_a INNER JOIN pegawai in_p ON in_a.pegawai = in_p.id WHERE in_p.id = pgw.id and in_a.kalendar between :tanggalAwal and :tanggalAkhir and in_a.status = 'CUTI') as cuti " +
			"FROM pegawai pgw INNER JOIN penduduk pdk ON pgw.penduduk = pdk.id " +
			"INNER JOIN unit_kerja uk ON pgw.unit_kerja = uk.id " +
			"LEFT JOIN unit_kerja uk2 ON uk.parent = uk2.id " +
			"WHERE uk.singkatan = :kode OR uk2.singkatan = :kode ORDER BY hadir DESC")
	List<RekapAbsen> rekapByUnitKerja(@Param("kode") String kode, @Param("tanggalAwal") Date awal, @Param("tanggalAkhir") Date akhir);

	@Query(nativeQuery = true, value = "SELECT pgw.nip as nip, pdk.nama as nama, uk.nama as nama_unit_kerja" +
			", (SELECT COUNT(*) FROM kalendar WHERE tanggal between :tanggalAwal and :tanggalAkhir) as jumlah_hari " +
			", (SELECT COUNT(*) FROM absen in_a INNER JOIN pegawai in_p ON in_a.pegawai = in_p.id WHERE in_p.id = pgw.id and in_a.kalendar between :tanggalAwal and :tanggalAkhir and in_a.status = 'HADIR') as hadir " +
			", (SELECT COUNT(*) FROM absen in_a INNER JOIN pegawai in_p ON in_a.pegawai = in_p.id WHERE in_p.id = pgw.id and in_a.kalendar between :tanggalAwal and :tanggalAkhir and in_a.status ='HADIR' and (in_a.pagi > '07:30' Or in_a.pagi is null)) as terlambat " +
			", (SELECT COUNT(*) FROM absen in_a INNER JOIN pegawai in_p ON in_a.pegawai = in_p.id WHERE in_p.id = pgw.id and in_a.kalendar between :tanggalAwal and :tanggalAkhir and in_a.status = 'HADIR' and (in_a.sore < '16:00' Or in_a.sore is null)) as pulang " +
			", (SELECT COUNT(*) FROM absen in_a INNER JOIN pegawai in_p ON in_a.pegawai = in_p.id WHERE in_p.id = pgw.id and in_a.kalendar between :tanggalAwal and :tanggalAkhir and in_a.status = 'TUGAS_LUAR') as tugas_luar " +
			", (SELECT COUNT(*) FROM absen in_a INNER JOIN pegawai in_p ON in_a.pegawai = in_p.id WHERE in_p.id = pgw.id and in_a.kalendar between :tanggalAwal and :tanggalAkhir and in_a.status = 'SAKIT') as sakit " +
			", (SELECT COUNT(*) FROM absen in_a INNER JOIN pegawai in_p ON in_a.pegawai = in_p.id WHERE in_p.id = pgw.id and in_a.kalendar between :tanggalAwal and :tanggalAkhir and in_a.status = 'IZIN') as izin " +
			", (SELECT COUNT(*) FROM absen in_a INNER JOIN pegawai in_p ON in_a.pegawai = in_p.id WHERE in_p.id = pgw.id and in_a.kalendar between :tanggalAwal and :tanggalAkhir and in_a.status = 'CUTI') as cuti " +
			"FROM pegawai pgw INNER JOIN penduduk pdk ON pgw.penduduk = pdk.id " +
			"INNER JOIN unit_kerja uk ON pgw.unit_kerja = uk.id " +
			"LEFT JOIN unit_kerja uk2 ON uk.parent = uk2.id " +
			"ORDER BY uk.nama, hadir DESC")
	List<RekapAbsen> rekap(@Param("tanggalAwal") Date awal, @Param("tanggalAkhir") Date akhir);
}
