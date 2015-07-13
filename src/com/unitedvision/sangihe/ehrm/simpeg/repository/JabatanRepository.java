package com.unitedvision.sangihe.ehrm.simpeg.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.unitedvision.sangihe.ehrm.EntityNotExistException;
import com.unitedvision.sangihe.ehrm.simpeg.Jabatan;
import com.unitedvision.sangihe.ehrm.simpeg.UnitKerja;

public interface JabatanRepository extends JpaRepository<Jabatan, Long> {

	List<Jabatan> findByUnitKerja(UnitKerja unitKerja) throws EntityNotExistException;

}
