package com.unitedvision.sangihe.ehrm.sppd.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.unitedvision.sangihe.ehrm.EntityNotExistException;
import com.unitedvision.sangihe.ehrm.sppd.Sppd;

public interface SppdRepository extends JpaRepository<Sppd, Long> {

	Sppd findByNomor(String nomor) throws EntityNotExistException;

}
