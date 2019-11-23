package com.socegen.employee.service;

import java.util.List;

import org.socgen.domainobjects.Employee;
import org.socgen.domainobjects.EmployeeRegistrationRequest;
import org.socgen.domainobjects.EmployeeRegistrationResponse;
import org.springframework.stereotype.Service;

@Service
public interface EmployeeService {

	EmployeeRegistrationResponse register(EmployeeRegistrationRequest employeeRegistrationRequest, String uuId);

	List<Employee> findAll(String uuId);

	
}
