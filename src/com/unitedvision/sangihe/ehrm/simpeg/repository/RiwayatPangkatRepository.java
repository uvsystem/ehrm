package com.unitedvision.sangihe.ehrm.simpeg.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.unitedvision.sangihe.ehrm.simpeg.Pangkat;
import com.unitedvision.sangihe.ehrm.simpeg.Pegawai;
import com.unitedvision.sangihe.ehrm.simpeg.RiwayatPangkat;

public interface RiwayatPangkatRepository extends JpaRepository<RiwayatPangkat, Long> {

	List<RiwayatPangkat> findByPegawai(Pegawai pegawai);

	List<RiwayatPangkat> findByPegawai_Nip(String nip);

	@Modifying
	@Query("DELETE FROM RiwayatPangkat rp WHERE rp.pegawai.nip = ?1 AND rp.pangkat = ?2")
	void deleteByPegawai_NipAndPangkat(String nip, Pangkat pangkat);

}
