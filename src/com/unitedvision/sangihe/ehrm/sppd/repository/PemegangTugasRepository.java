package com.unitedvision.sangihe.ehrm.sppd.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.unitedvision.sangihe.ehrm.sppd.PemegangTugas;

public interface PemegangTugasRepository extends JpaRepository<PemegangTugas, Long> {

	PemegangTugas findByPegawai_NipAndSuratTugas_Nomor(String nip, String nomor);

}
