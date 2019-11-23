package com.socegen.employee.controller;

import java.util.List;

import org.socgen.domainobjects.Employee;
import org.socgen.domainobjects.EmployeeRegistrationRequest;
import org.socgen.domainobjects.EmployeeRegistrationResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.socegen.employee.service.EmployeeService;
import com.socegen.employee.util.UniqueIdGenerator;

@CrossOrigin(origins = "http://localhost:8048")
@RequestMapping("/v1")
@RestController
public class EmployeeController {

	@Autowired
	EmployeeService employeeService;
	
	@RequestMapping(value = "/heartbeat", method = RequestMethod.GET)
	public String heartBeat() {
		return "I'm Alive";
	}
	
	@RequestMapping(value = "/register", method = RequestMethod.POST)
	public EmployeeRegistrationResponse register(@RequestBody EmployeeRegistrationRequest employeeRegistrationRequest) {
		UniqueIdGenerator uniqueIdGenerator = new UniqueIdGenerator();
		String uuId = uniqueIdGenerator.getUUID();
		return employeeService.register(employeeRegistrationRequest, uuId);
	}
	
	@RequestMapping(value = "/allemployees", method = RequestMethod.GET)
	public List<Employee> getAllEmployees() {
		UniqueIdGenerator uniqueIdGenerator = new UniqueIdGenerator();
		String uuId = uniqueIdGenerator.getUUID();
		return employeeService.findAll(uuId);
	}
	
	
	
}
