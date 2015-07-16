package com.unitedvision.sangihe.ehrm.interceptor;

import java.util.List;

import javax.persistence.PersistenceException;

import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class RepositoryInterceptor {

	@AfterReturning(
		pointcut = "execution(public * com.unitedvision.sangihe.ehrm.*.repository.*.find*(..))",
		returning = "object"
	)
	public void nullEntityReturned(Object object) throws PersistenceException {
		
		if (object == null)
			throw new PersistenceException("Data tidak ditemukan");
		
		if ((object instanceof List) && ((List<?>)object).size() <= 0)
			throw new PersistenceException("Tidak ada data yang ditemukan");
	}

	@AfterThrowing(
		pointcut = "execution(public * com.unitedvision.sangihe.ehrm.*.repository.*.save(..))",
		throwing = "ex")
	public void errorThrown(PersistenceException ex) throws PersistenceException {
		throw new PersistenceException(createMessage(ex));
	}
	
	private String createMessage(Exception ex) {
		String key = ex.getMessage();
		
		if (key.contains("username")) {
			return "Username yang anda masukan sudah digunakan";
		} else if (key.contains("nip")) {
			return "NIP yang anda masukan sudah digunakan";
		} else if (key.contains("nama")) {
			return "Nama yang anda masukan sudah digunakan";
		} else if (key.contains("akronim")) {
			return "Singkatan yang anda masukan sudah digunakan";
		} else {
			return "Error";
		}
	}

}
