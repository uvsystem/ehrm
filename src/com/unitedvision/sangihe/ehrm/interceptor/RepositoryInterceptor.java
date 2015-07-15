package com.unitedvision.sangihe.ehrm.interceptor;

import java.util.List;

import javax.persistence.PersistenceException;

import org.aspectj.lang.annotation.AfterReturning;
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
}
