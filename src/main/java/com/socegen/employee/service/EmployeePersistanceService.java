package com.socegen.employee.service;

import java.util.List;

import com.socegen.employee.model.db.Employee;

public interface EmployeePersistanceService {

	Employee saveEntity(Employee employeeEntity);

	List<Employee> findAll();

}
