package com.socegen.employee.service.util;

import org.socgen.domainobjects.Error;
import org.socgen.domainobjects.ErrorType;
import org.socgen.domainobjects.StatusCode;
import org.socgen.domainobjects.StatusType;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

@Component
public class EmployeeServiceUtil {

	public static StatusType sendSuccessStatusType(String successMsg) {
		StatusType statusType = new StatusType();
		statusType.setMessage(successMsg);
		statusType.setStatusCode(StatusCode.SUCCESS);
		return statusType;
	}

	public static StatusType setNoContentStatusType(String msg, String uuId) {
		StatusType statusType = new StatusType();
		ErrorType errorType = new ErrorType();
		Error error = new Error();
		error.setErrorCode(HttpStatus.NO_CONTENT.toString());
		error.setErrorMsg(msg);
		error.setUuid(uuId);
		errorType.getError().add(error);
		statusType.setErrors(errorType);
		statusType.setMessage(msg);
		statusType.setStatusCode(StatusCode.SUCCESS);
		return statusType;
	}

	public static StatusType sendErrorStatusType(Exception e, String uuId) {
		StatusType statusType = new StatusType();
		ErrorType errorType = new ErrorType();
		Error error = new Error();
		error.setErrorMsg(e.getMessage());
		error.setUuid(uuId);
		error.setErrorCode(HttpStatus.INTERNAL_SERVER_ERROR.toString());
		errorType.getError().add(error);
		statusType.setErrors(errorType);
		statusType.setMessage(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase());
		statusType.setStatusCode(StatusCode.FAILED);
		return statusType;
	}
	
}
