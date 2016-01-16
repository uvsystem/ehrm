package com.unitedvision.sangihe.ehrm.manajemen;

import java.util.List;

import javax.persistence.PersistenceException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.unitedvision.sangihe.ehrm.CodeUtil;
import com.unitedvision.sangihe.ehrm.UnauthenticatedAccessException;
import com.unitedvision.sangihe.ehrm.manajemen.Token.StatusToken;
import com.unitedvision.sangihe.ehrm.manajemen.repository.OperatorRepository;
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
	@Autowired
	private OperatorRepository operatorRepository;
	
	@Override
	@Transactional(readOnly = false)
	public Pegawai login(String username) {
		Pegawai pegawai = pegawaiRepository.findByNip(username);
		
		try {
			pegawai.setListOperator(operatorRepository.findByPegawai(pegawai));
		} catch(PersistenceException ex) {}
		
		return pegawai;
	}

	@Override
	public Token get(String token) throws OutOfDateEntityException, UnauthenticatedAccessException {
		if (token.equals(CodeUtil.password))
			return Token.createAdmin();

		Token tokenObject = tokenRepository.findByToken(token);
		
		if (tokenObject.getStatus().equals(StatusToken.LOCKED))
			throw new UnauthenticatedAccessException();

		tokenObject.extend();
		
		tokenRepository.save(tokenObject);
		
		try {
			Pegawai pegawai = tokenObject.getpegawai();
			pegawai.setListOperator(operatorRepository.findByPegawai(pegawai));
			tokenObject.setPegawai(pegawai);
		} catch(PersistenceException e){ }

		return tokenObject;
	}

	@Override
	@Transactional(readOnly = false)
	public Token create(Pegawai pegawai) {
		Token token = new Token();
		token.setPegawai(pegawai);
		token.generateToken();
		
		tokenRepository.save(token);

		try {
			List<Operator> daftarOperator = operatorRepository.findByPegawai(pegawai);
			pegawai.setListOperator(daftarOperator);
			token.setPegawai(pegawai);
		} catch(PersistenceException e){ }
		
		return token;
	}
	
	@Override
	@Transactional(readOnly = false)
	public Token create(String nip, String password) throws UnauthenticatedAccessException {
		if (nip.equals(CodeUtil.username) && password.equals(CodeUtil.password))
			return Token.createAdmin();
		
		Pegawai pegawai = pegawaiRepository.findByNip(nip);
		if (nip.equals(pegawai.getNip()) && password.equals(pegawai.getPassword()))
			return create(pegawai);
		
		throw new UnauthenticatedAccessException("Username/Password salah!");
	}

	@Override
	@Transactional(readOnly = false)
	public Token lock(String token) {
		Token tokenObject = tokenRepository.findByToken(token);
		tokenObject.setStatus(StatusToken.LOCKED);
		
		return tokenRepository.save(tokenObject);
	}

	@Override
	public void hapus() {
		tokenRepository.deleteAll();
	}
}
