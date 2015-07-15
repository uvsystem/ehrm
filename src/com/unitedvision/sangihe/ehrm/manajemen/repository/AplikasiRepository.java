package com.unitedvision.sangihe.ehrm.manajemen.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.unitedvision.sangihe.ehrm.manajemen.Aplikasi;

public interface AplikasiRepository extends JpaRepository<Aplikasi, Long> {

	Aplikasi findByKode(String kode);

	List<Aplikasi> findByKodeLike(String kode);

}
