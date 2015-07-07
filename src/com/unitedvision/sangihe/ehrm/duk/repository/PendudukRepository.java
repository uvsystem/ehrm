package com.unitedvision.sangihe.ehrm.duk.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.unitedvision.sangihe.ehrm.duk.Penduduk;

public interface PendudukRepository extends JpaRepository<Penduduk, Long> {

	Penduduk findByNik(String nik);
	
}
