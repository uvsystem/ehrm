package com.unitedvision.sangihe.ehrm.simpeg.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.unitedvision.sangihe.ehrm.simpeg.UnitKerja;

public interface UnitKerjaRepository extends JpaRepository<UnitKerja, Long> {

	UnitKerja findBySingkatan(String singkatan);

}
