package com.unitedvision.sangihe.ehrm.interceptor;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ResponseBody;

import com.unitedvision.sangihe.ehrm.EntityRestMessage;
import com.unitedvision.sangihe.ehrm.ListEntityRestMessage;
import com.unitedvision.sangihe.ehrm.RestMessage;

@Aspect
@Component
public class MessageConverter {
	
	@Around("execution(public com.unitedvision.sangihe.ehrm.RestMessage com.unitedvision.sangihe.ehrm.*.*.*(..) throws com.unitedvision.sangihe.ehrm.ApplicationException, javax.persistence.PersistenceException)")
	public @ResponseBody RestMessage process(ProceedingJoinPoint jointPoint) {
		try {
			return (RestMessage) jointPoint.proceed();
		} catch (Throwable e) {
			e.printStackTrace();
			return RestMessage.error((Exception)e);
		}
	}

	@Around("execution(public com.unitedvision.sangihe.ehrm.EntityRestMessage com.unitedvision.sangihe.ehrm.*.*.*(..) throws com.unitedvision.sangihe.ehrm.ApplicationException, javax.persistence.PersistenceException)")
	public @ResponseBody EntityRestMessage<?> getEntity(ProceedingJoinPoint jointPoint) {
		try {
			return (EntityRestMessage<?>) jointPoint.proceed();
		} catch (Throwable e) {
			e.printStackTrace();
			return EntityRestMessage.entityError((Exception)e);
		}
	}
	
	@Around("execution(public com.unitedvision.sangihe.ehrm.ListEntityRestMessage com.unitedvision.sangihe.ehrm.*.*.*(..) throws com.unitedvision.sangihe.ehrm.ApplicationException, javax.persistence.PersistenceException)")
	public @ResponseBody ListEntityRestMessage<?> getListEntity(ProceedingJoinPoint jointPoint) {
		try {
			return (ListEntityRestMessage<?>) jointPoint.proceed();
		} catch (Throwable e) {
			e.printStackTrace();
			return ListEntityRestMessage.listEntityError((Exception)e);
		}
	}
	
}
