package com.unitedvision.sangihe.ehrm;


public class EntityNotExistException extends ApplicationException {

	private static final long serialVersionUID = 1L;

	public EntityNotExistException() {
		super();
	}

	public EntityNotExistException(String message) {
		super(message);
	}

}
