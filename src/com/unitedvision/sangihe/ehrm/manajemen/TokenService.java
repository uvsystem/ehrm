package com.unitedvision.sangihe.ehrm.manajemen;

import com.unitedvision.sangihe.ehrm.EntityNotExistException;
import com.unitedvision.sangihe.ehrm.OutOfDateEntityException;

public interface TokenService {

	Token get(String token) throws EntityNotExistException, OutOfDateEntityException;
	Token create(String nip);
	void lock(String token);

}
