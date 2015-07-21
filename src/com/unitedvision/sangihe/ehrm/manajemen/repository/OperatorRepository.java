package com.unitedvision.sangihe.ehrm.manajemen.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.unitedvision.sangihe.ehrm.manajemen.Aplikasi;
import com.unitedvision.sangihe.ehrm.manajemen.Operator;
import com.unitedvision.sangihe.ehrm.manajemen.Operator.Role;
import com.unitedvision.sangihe.ehrm.simpeg.Pegawai;

public interface OperatorRepository extends JpaRepository<Operator, Long> {

	List<Operator> findByPegawai(Pegawai pegawai);

	List<Operator> findByPegawai_Nip(String nip);

	List<Operator> findByAplikasiAndRole(Aplikasi aplikasi, Operator.Role role);

	List<Operator> findByAplikasi_KodeAndRole(String kode, Role operator);

	@Modifying
	@Query("DELETE FROM Operator o WHERE o.pegawai.nip = ?1 AND o.aplikasi.kode = ?2")
	void deleteByPegawai_NipAndAplikasi_Kode(String nip, String kode);

}
