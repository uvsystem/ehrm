package com.unitedvision.sangihe.ehrm.simpeg.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.unitedvision.sangihe.ehrm.simpeg.Pegawai;
import com.unitedvision.sangihe.ehrm.simpeg.RiwayatJabatan;
import com.unitedvision.sangihe.ehrm.simpeg.RiwayatPangkat;

public interface RiwayatJabatanRepository extends JpaRepository<RiwayatJabatan, Long> {

	List<RiwayatPangkat> findByPegawai(Pegawai pegawai);

}
