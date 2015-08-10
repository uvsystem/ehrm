package com.unitedvision.sangihe.ehrm.sppd.repository;

import java.sql.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.unitedvision.sangihe.ehrm.sppd.RekapSppd;

public interface RekapSppdRepository extends JpaRepository<RekapSppd, String> {

	@Query(nativeQuery = true,
		value="SELECT pgw.nip as nip, pdk.nama as nama, uk.nama as unit_kerja"
				+ ", (SELECT COUNT(*) FROM sppd in_s "
				+ "INNER JOIN pemegang_tugas p_tgs ON in_s.pemegang_tugas = p_tgs.id "
				+ "WHERE p_tgs.pegawai = pgw.id AND in_s.tanggal_berangkat BETWEEN :awal AND :akhir) as jumlah_sppd"
				+ ", (SELECT COUNT(*) FROM absen a "
				+ "WHERE a.pegawai = pgw.id AND a.status = 'TUGAS_LUAR') as jumlah_tugas_luar "
				+ "FROM sppd s "
				+ "INNER JOIN pemegang_tugas ptgs ON s.pemegang_tugas = ptgs.id "
				+ "INNER JOIN pegawai pgw ON ptgs.pegawai = pgw.id "
				+ "INNER JOIN penduduk pdk ON pgw.penduduk = pdk.id "
				+ "INNER JOIN unit_kerja uk ON pgw.unit_kerja = uk.id "
				+ "ORDER BY jumlah_sppd DESC")
	List<RekapSppd> rekap(@Param("awal") Date awal, @Param("akhir") Date akhir);

}
