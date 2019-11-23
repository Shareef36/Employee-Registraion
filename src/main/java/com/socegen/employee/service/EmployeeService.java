package com.socegen.employee.service;

import org.socgen.domainobjects.EmployeeRegistrationRequest;
import org.socgen.domainobjects.EmployeeRegistrationResponse;
import org.springframework.stereotype.Service;

@Service
public interface EmployeeService {

	EmployeeRegistrationResponse register(EmployeeRegistrationRequest employeeRegistrationRequest, String uuId);

	
}
