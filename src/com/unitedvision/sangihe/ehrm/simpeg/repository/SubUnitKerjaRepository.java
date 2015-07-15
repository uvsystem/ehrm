package com.unitedvision.sangihe.ehrm.simpeg.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.unitedvision.sangihe.ehrm.simpeg.SubUnitKerja;
import com.unitedvision.sangihe.ehrm.simpeg.UnitKerja;

public interface SubUnitKerjaRepository extends JpaRepository<SubUnitKerja, Long> {

	List<SubUnitKerja> findByUnitKerja(UnitKerja unitKerja);

}
