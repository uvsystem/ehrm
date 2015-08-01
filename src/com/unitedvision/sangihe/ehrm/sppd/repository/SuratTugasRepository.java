package com.unitedvision.sangihe.ehrm.sppd.repository;

import java.sql.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.unitedvision.sangihe.ehrm.simpeg.Pegawai;
import com.unitedvision.sangihe.ehrm.sppd.SuratTugas;
import com.unitedvision.sangihe.ehrm.sppd.SuratTugas.Status;

public interface SuratTugasRepository extends JpaRepository<SuratTugas, Long> {

	SuratTugas findByNomor(String nomorSuratTugas);

	@Query("FROM PemegangTugas pt JOIN pt.suratTugas st WHERE pt.pegawai = ?1")
	List<SuratTugas> findByPegawai(Pegawai pegawai);

	List<SuratTugas> findByStatusAndTanggalBetween(Status status,Date sekarang, Date duaTahunLalu);

	List<SuratTugas> findByTanggalBetween(Date tanggalAwal, Date tanggalAkhir);

	@Query("FROM PemegangTugas pt JOIN pt.suratTugas st WHERE pt.pegawai.unitKerja.singkatan = ?1 AND st.tanggal BETWEEN ?2 AND ?3")
	List<SuratTugas> findBySatuanKerja(String kode, Date awal, Date akhir);

	List<SuratTugas> findByNomorContaining(String keyword);

}
