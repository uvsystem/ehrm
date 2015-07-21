package com.unitedvision.sangihe.ehrm.simpeg.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.unitedvision.sangihe.ehrm.simpeg.Pegawai;
import com.unitedvision.sangihe.ehrm.simpeg.RiwayatJabatan;

public interface RiwayatJabatanRepository extends JpaRepository<RiwayatJabatan, Long> {

	List<RiwayatJabatan> findByPegawai(Pegawai pegawai);

	List<RiwayatJabatan> findByPegawai_Nip(String nip);

	@Modifying
	@Query("DELETE FROM RiwayatJabatan rj WHERE rj.pegawai.nip = ?1 AND rj.jabatan.id = ?2")
	void deleteByPegawai_NipAndJabatan_Id(String nip, Long idJabatan);

}
