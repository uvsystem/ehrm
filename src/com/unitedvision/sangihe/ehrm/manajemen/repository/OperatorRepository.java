package com.unitedvision.sangihe.ehrm.manajemen.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.unitedvision.sangihe.ehrm.manajemen.Operator;
import com.unitedvision.sangihe.ehrm.simpeg.Pegawai;

public interface OperatorRepository extends JpaRepository<Operator, Long> {

	List<Operator> findByPegawai(Pegawai pegawai);

}
