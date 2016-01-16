package com.unitedvision.sangihe.ehrm.manajemen;

import com.unitedvision.sangihe.ehrm.OutOfDateEntityException;
import com.unitedvision.sangihe.ehrm.UnauthenticatedAccessException;
import com.unitedvision.sangihe.ehrm.simpeg.Pegawai;

public interface TokenService {

	Token get(String token) throws OutOfDateEntityException, UnauthenticatedAccessException;

	Token create(Pegawai pegawai);
	
	Token create(String nip, String password) throws UnauthenticatedAccessException;
	
	Token lock(String token);
	
	void hapus();
	
	Pegawai login(String username);

}
