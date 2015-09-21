package com.unitedvision.sangihe.ehrm.simpeg.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.unitedvision.sangihe.ehrm.simpeg.UnitKerja;

public interface UnitKerjaRepository extends JpaRepository<UnitKerja, Long> {

	@Query("FROM UnitKerja uk WHERE uk.nama LIKE CONCAT('%', :keyword, '%') OR uk.singkatan LIKE CONCAT('%', :keyword, '%')")
	List<UnitKerja> findByNamaContainingOrSingkatanContaining(@Param("keyword") String keyword);

	UnitKerja findBySingkatan(String kode);
	
	@Modifying
	@Query("DELETE FROM UnitKerja uk WHERE uk.singkatan = ?1")
	void deleteBySingkatan(String kode);

	@Query("FROM UnitKerja uk WHERE uk.parent = :unitKerja OR uk.parent.parent = :unitKerja")
	List<UnitKerja> findByParent(@Param("unitKerja") UnitKerja unitKerja);

}
