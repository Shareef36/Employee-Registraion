package com.socegen.employee.service.impl;

import java.util.Objects;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.socgen.domainobjects.Employee;
import org.socgen.domainobjects.EmployeeRegistrationRequest;
import org.socgen.domainobjects.EmployeeRegistrationResponse;
import org.socgen.domainobjects.StatusType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.introspect.VisibilityChecker;
import com.socegen.employee.constant.EmployeeConstants;
import com.socegen.employee.exception.EmployeeException;
import com.socegen.employee.service.EmployeePersistanceService;
import com.socegen.employee.service.EmployeeService;
import com.socegen.employee.service.util.EmployeeServiceUtil;
import com.socegen.employee.util.EmployeeServiceValidator;

@Component
public class EmployeeServiceImpl implements EmployeeService{
	
	private static final Logger logger = LogManager.getLogger(EmployeeServiceImpl.class);
	
	@Autowired
	EmployeeServiceValidator employeeServiceValidator;
	
	@Autowired
	EmployeePersistanceService employeePersistanceService;
	
	@Override
	public EmployeeRegistrationResponse register(EmployeeRegistrationRequest employeeRegistrationRequest, String uuId) {
		EmployeeRegistrationResponse response = new EmployeeRegistrationResponse();
		try {
			StatusType statusType = employeeServiceValidator.validateRequestParams(employeeRegistrationRequest, uuId);
			if (Objects.nonNull(statusType)) {
				response.setStatusType(statusType);
			} else {
				Employee employee = registerEmployee(employeeRegistrationRequest, uuId);
				response.setEmployeeDetails(employee);
				if(Objects.nonNull(employee)) {
					response.setStatusType(EmployeeServiceUtil.sendSuccessStatusType(EmployeeConstants.STATUS_MESSAGE_OK));
				} else {
					response.setStatusType(EmployeeServiceUtil.setNoContentStatusType("Failed", uuId));
				}
			}
		} catch (Exception e) {
			logger.error(new EmployeeException(e));
			response.setStatusType(EmployeeServiceUtil.sendErrorStatusType(e, uuId));
			return response;
		}
		return response;
	}

	private Employee registerEmployee(EmployeeRegistrationRequest employeeRegistrationRequest,
			String uuId) {
		logger.info(uuId+": Save from Request Obj to Entity Obj");
		Employee employee = employeeRegistrationRequest.getEmployee();
		if(Objects.nonNull(employee)){
			com.socegen.employee.model.db.Employee employeeEntity = (com.socegen.employee.model.db.Employee) convertObject(employee, com.socegen.employee.model.db.Employee.class);
			com.socegen.employee.model.db.Employee saveEntity = employeePersistanceService.saveEntity(employeeEntity);
			if(Objects.nonNull(saveEntity)) {
				logger.info(uuId+": Employee details persisted successfully");
				return (Employee) convertObject(saveEntity, Employee.class);
			} else {
				logger.info(uuId+": Failed to persist Employee details");
			}
		}
		return null;
		
	}

	private Object convertObject(Object employee, Class<?> clazz) {
		ObjectMapper mapper = new ObjectMapper();
		mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
		mapper.setVisibility(VisibilityChecker.Std.defaultInstance().withFieldVisibility(JsonAutoDetect.Visibility.ANY));
		return mapper.convertValue(employee, clazz);
	}
	
	
}
