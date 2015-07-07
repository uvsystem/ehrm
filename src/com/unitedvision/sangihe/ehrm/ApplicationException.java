package com.unitedvision.sangihe.ehrm;

import java.io.Serializable;

public class ApplicationException extends Exception implements Serializable {

	private static final long serialVersionUID = 1L;

	public ApplicationException() {
		super();
	}

	public ApplicationException(String message) {
		super(message);
	}

}
