package com.r7.blacklist.exception;

public class CommonException extends Exception {

	private static final long serialVersionUID = 5746751520999684832L;

	public CommonException(String mensagem) {
		super(mensagem);
	}

	@Override
	public String getMessage() {
		return "{ \"success\": false, \"message\": \"" + super.getMessage() + "\" }";
	}
	
}
