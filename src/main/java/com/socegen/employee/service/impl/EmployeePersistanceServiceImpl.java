package com.socegen.employee.service.impl;

import java.util.Objects;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.socegen.employee.exception.EmployeeException;
import com.socegen.employee.model.db.Employee;
import com.socegen.employee.repository.EmployeeRepository;
import com.socegen.employee.service.EmployeePersistanceService;

@Component
public class EmployeePersistanceServiceImpl implements EmployeePersistanceService{

	@Autowired
	EmployeeRepository employeeRepository;
	
	@Override
	public Employee saveEntity(Employee employeeEntity) {
		if(Objects.nonNull(employeeEntity)) {
			Optional<Employee> dbObj = employeeRepository.findByFirstNameAndLastNameAndDateOfBirth(employeeEntity.getFirstName(), employeeEntity.getLastName(), employeeEntity.getDateOfBirth());;
			if(dbObj.isPresent()) {
				throw new EmployeeException("Already Exists");
			}
			return employeeRepository.save(employeeEntity);
		}
		return employeeEntity;
	}

}
