package com.socegen.employee.exception;

import java.io.StringWriter;

public class EmployeeException extends RuntimeException {

	private static final long serialVersionUID = -958848778630176141L;
	private final String errorCode;
	private final String detail;

	public EmployeeException() {
		// default method
		errorCode = null;
		detail = null;
	}

	public EmployeeException(String aMessage) {
		super(aMessage);
		errorCode = null;
		detail = null;
	}

	public EmployeeException(Exception aCause) {
		super(aCause);
		errorCode = null;
		detail = convertException(aCause);
	}

	public EmployeeException(Throwable t) {
		super(t);
		errorCode = null;
		detail = null;
		t.getMessage();
	}

	public EmployeeException(String aMessage, Exception aCause) {
		super(aMessage, aCause);
		errorCode = null;
		detail = convertException(aCause);
	}

	public String getErrorCode() {
		return errorCode;
	}

	public String getDetail() {
		return detail;
	}

	private String convertException(Exception e) {
		if (e == null) {
			return null;
		}
		StringWriter sw = new StringWriter();
		e.printStackTrace();
		return sw.toString();

	}

}
