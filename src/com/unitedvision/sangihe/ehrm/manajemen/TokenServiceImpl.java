package com.unitedvision.sangihe.ehrm.manajemen;

import javax.persistence.PersistenceException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.unitedvision.sangihe.ehrm.DateUtil;
import com.unitedvision.sangihe.ehrm.EntityNotExistException;
import com.unitedvision.sangihe.ehrm.UnauthenticatedAccessException;
import com.unitedvision.sangihe.ehrm.manajemen.Token.StatusToken;
import com.unitedvision.sangihe.ehrm.manajemen.repository.TokenRepository;
import com.unitedvision.sangihe.ehrm.simpeg.Pegawai;
import com.unitedvision.sangihe.ehrm.simpeg.repository.PegawaiRepository;
import com.unitedvision.sangihe.ehrm.OutOfDateEntityException;

@Service
@Transactional(readOnly = true)
public class TokenServiceImpl implements TokenService {
	
	@Autowired
	private TokenRepository tokenRepository;
	@Autowired
	private PegawaiRepository pegawaiRepository;

	@Override
	public Token get(String token) throws EntityNotExistException, OutOfDateEntityException, UnauthenticatedAccessException {
		Token tokenObject;
		
		try {
			tokenObject = tokenRepository.findByToken(token);
			
			if (tokenObject == null)
				throw new PersistenceException("Token tidak ditemukan");
		} catch (PersistenceException e) {
			throw new EntityNotExistException(e.getMessage());
		}
		
		if (tokenObject.getStatus().equals(StatusToken.LOCKED))
			throw new UnauthenticatedAccessException();

		tokenObject.extend();
		
		return tokenRepository.save(tokenObject);
	}

	@Override
	@Transactional(readOnly = false)
	public Token create(Pegawai pegawai) {
		Token token = new Token();
		token.setPegawai(pegawai);
		token.setTanggalBuat(DateUtil.getNow());
		token.setStatus(StatusToken.AKTIF);
		token.generateExpireDate();
		token.generateToken();
		
		return tokenRepository.save(token);
	}
	
	@Override
	@Transactional(readOnly = false)
	public Token create(String nip) throws EntityNotExistException {
		Pegawai pegawai = pegawaiRepository.findByNip(nip);
		
		return create(pegawai);
	}

	@Override
	@Transactional(readOnly = false)
	public Token lock(String token) throws EntityNotExistException {
		Token tokenObject = tokenRepository.findByToken(token);
		tokenObject.setStatus(StatusToken.LOCKED);
		
		return tokenRepository.save(tokenObject);
	}

}
