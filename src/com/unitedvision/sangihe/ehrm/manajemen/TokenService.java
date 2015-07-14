package com.unitedvision.sangihe.ehrm.manajemen;

import com.unitedvision.sangihe.ehrm.EntityNotExistException;
import com.unitedvision.sangihe.ehrm.OutOfDateEntityException;
import com.unitedvision.sangihe.ehrm.UnauthenticatedAccessException;
import com.unitedvision.sangihe.ehrm.simpeg.Pegawai;

public interface TokenService {

	Token get(String token) throws EntityNotExistException, OutOfDateEntityException, UnauthenticatedAccessException;
	Token create(Pegawai pegawai) throws EntityNotExistException;
	Token create(String nip) throws EntityNotExistException;
	Token lock(String token) throws EntityNotExistException;
	Pegawai login(String username) throws EntityNotExistException;

}
