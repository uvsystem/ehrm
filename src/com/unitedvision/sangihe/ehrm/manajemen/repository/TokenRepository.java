package com.unitedvision.sangihe.ehrm.manajemen.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.unitedvision.sangihe.ehrm.EntityNotExistException;
import com.unitedvision.sangihe.ehrm.manajemen.Token;

public interface TokenRepository extends JpaRepository<Token, Long> {

	Token findByToken(String token) throws EntityNotExistException;

}
