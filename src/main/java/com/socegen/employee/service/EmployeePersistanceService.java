package com.socegen.employee.service;

import com.socegen.employee.model.db.Employee;

public interface EmployeePersistanceService {

	Employee saveEntity(Employee employeeEntity);

}
