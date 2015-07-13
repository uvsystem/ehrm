package com.unitedvision.sangihe.ehrm.sppd.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.unitedvision.sangihe.ehrm.EntityNotExistException;
import com.unitedvision.sangihe.ehrm.simpeg.Pegawai;
import com.unitedvision.sangihe.ehrm.sppd.SuratTugas;

public interface SuratTugasRepository extends JpaRepository<SuratTugas, Long> {

	SuratTugas findByNomor(String nomorSuratTugas) throws EntityNotExistException;

	@Query("FROM PemegangTugas pt JOIN pt.suratTugas st WHERE pt.pegawai = ?1")
	List<SuratTugas> findByPegawai(Pegawai pegawai) throws EntityNotExistException;

}
