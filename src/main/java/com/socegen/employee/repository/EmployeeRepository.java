package com.socegen.employee.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.socegen.employee.model.db.Employee;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Integer>{

	Optional<Employee> findByFirstNameAndLastNameAndDateOfBirth(String firstName, String lastName, String dateOfBirth);


}
