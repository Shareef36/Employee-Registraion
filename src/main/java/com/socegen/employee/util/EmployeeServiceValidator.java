package com.socegen.employee.util;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.socgen.domainobjects.Error;
import org.socgen.domainobjects.ErrorType;
import org.socgen.domainobjects.StatusCode;
import org.socgen.domainobjects.StatusType;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import com.socegen.employee.constant.EmployeeConstants;

@Component
public class EmployeeServiceValidator {

	public StatusType validateRequestParams(Object request, String uuId) throws Exception {
		StatusType statusType = null;
		ErrorType errorType = new ErrorType();
		validateRequestParams(request, uuId, errorType);
		if (!errorType.getError().isEmpty()) {
			statusType = new StatusType();
			statusType.setErrors(errorType);
			statusType.setMessage(HttpStatus.BAD_REQUEST.name());
			statusType.setStatusCode(StatusCode.FAILED);
		}
		return statusType;
	}

	public ErrorType validateRequestParams(Object clazz, String uuId, ErrorType errorType) throws Exception {
		Field[] fields = clazz.getClass().getDeclaredFields();
		for (Field field : fields) {
			Object value = null;
			field.setAccessible(true);
			value = field.get(clazz);
			if (checkNullZeroOrString(value, field.getName())) {
				setErrorStatus(field.getName(), uuId, errorType);
			}
			if (value instanceof List) {
				if (((List<?>) value).isEmpty()) {
					setErrorStatus(field.getName(), uuId, errorType);
				} else {
					forEach((List<?>) value, errorType, uuId, field.getName());
				}
			} else if (value instanceof Number || value instanceof String) {
				value = value.toString().trim();
				checkNullOrEmpty(uuId, errorType, field.getName(), value);
			}
		}
		return errorType;
	}

	public boolean checkNullZeroOrString(Object value, String fieldName) {
		return Objects.isNull(value) || "0".equals(value.toString()) || "string".equalsIgnoreCase(value.toString());
	}

	public ErrorType forEach(List<?> values, ErrorType errorType, String uuId, String name) throws Exception {
		for (Object value : values) {
			if (checkNullZeroOrString(value, name)) {
				setErrorStatus(name, uuId, errorType);
			} else if (value instanceof Number) {
				checkNullOrEmpty(uuId, errorType, name, value);
			} else if (value instanceof String) {
				checkNullOrEmpty(uuId, errorType, name, value);
			} else {
				validateRequestParams(value, uuId, errorType);
			}
		}
		return errorType;
	}

	public void checkNullOrEmpty(String uuId, ErrorType errorType, String fieldName, Object value) {

		if (Objects.isNull(value)) {
			setErrorStatus(fieldName, uuId, errorType);
		}
		if (value instanceof ArrayList) {
			validateList(uuId, errorType, fieldName, value);
		}
		if (value instanceof String && value.toString().isEmpty() && !"id".equalsIgnoreCase(fieldName)) {
			setErrorStatus(fieldName, uuId, errorType);
		}
		if (value instanceof Long && ("0").equalsIgnoreCase(value.toString())) {
			setErrorStatus(fieldName, uuId, errorType);
		}
		if (value instanceof Double && ("0.0").equalsIgnoreCase(value.toString())) {
			setErrorStatus(fieldName, uuId, errorType);
		}
	}

	public void validateList(String uuId, ErrorType errorType, String fieldName, Object value) {
		List<?> list = (List<?>) value;
		if (list.isEmpty()) {
			setErrorStatus(fieldName, uuId, errorType);
		} else {
			int i = 1;
			for (Object emp : list) {
				checkNullOrEmpty(uuId, errorType, i++ + " emp", emp);
			}
		}
	}

	public void setErrorStatus(String msg, String uuId, ErrorType errorType) {
		Error error = new Error();
		error.setErrorMsg("'" + msg + EmployeeConstants.VALUE_IS_NULL_OR_EMPTY);
		error.setErrorCode(HttpStatus.BAD_REQUEST.toString());
		error.setUuid(uuId);
		errorType.getError().add(error);
	}

	
}
