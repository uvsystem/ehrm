package com.unitedvision.sangihe.ehrm.sppd.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.unitedvision.sangihe.ehrm.simpeg.Pegawai;
import com.unitedvision.sangihe.ehrm.sppd.Sppd;

public interface SppdRepository extends JpaRepository<Sppd, Long> {

	Sppd findByNomor(String nomor);

	List<Sppd> findByPemegangTugas_Pegawai(Pegawai pegawai);

}
