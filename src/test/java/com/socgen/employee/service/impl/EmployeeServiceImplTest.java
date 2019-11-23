package com.socgen.employee.service.impl;

import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.socgen.domainobjects.Employee;
import org.socgen.domainobjects.EmployeeRegistrationRequest;
import org.socgen.domainobjects.Gender;
import org.socgen.domainobjects.StatusType;

import com.socegen.employee.exception.EmployeeException;
import com.socegen.employee.service.EmployeePersistanceService;
import com.socegen.employee.service.impl.EmployeeServiceImpl;
import com.socegen.employee.util.EmployeeServiceValidator;

//@ExtendWith(MockitoExtension.class)
public class EmployeeServiceImplTest {

	@Mock
	EmployeeServiceValidator employeeServiceValidator;
	
	@Mock
	EmployeePersistanceService employeePersistanceService;

	@InjectMocks
	private EmployeeServiceImpl employeeServiceImpl;

	EmployeeRegistrationRequest request = null;
	com.socegen.employee.model.db.Employee entity = null;
	
	@BeforeEach
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		request = new EmployeeRegistrationRequest();
		Employee employee = new Employee();
		employee.setFirstName("Shareef");
		employee.setLastName("Tanguturi");
		employee.setDateOfBirth("11-07-1992");
		employee.setDepartment("IT-Programming Developement");
		employee.setGender(Gender.MALE);
		request.setEmployee(employee);
		
		entity = new com.socegen.employee.model.db.Employee();
		entity.setFirstName("Shareef");
		entity.setLastName("Tanguturi");
		entity.setDateOfBirth("11-07-1992");
		entity.setDepartment("IT-Programming Developement");
		entity.setGender(com.socegen.employee.model.db.Gender.MALE);
	}
	
	@Test
	public void testRegisterInvalidParams() throws Exception {
		Mockito.when(employeeServiceValidator.validateRequestParams(Mockito.any(), Mockito.any())).thenReturn(new StatusType());
		Mockito.when(employeePersistanceService.saveEntity(Mockito.any())).thenReturn(entity);
		assertNotNull(employeeServiceImpl.register(null, "123"));
	}
	
	@Test
	public void testRegister() throws Exception {
		Mockito.when(employeeServiceValidator.validateRequestParams(Mockito.any(), Mockito.any())).thenReturn(null);
		Mockito.when(employeePersistanceService.saveEntity(Mockito.any())).thenReturn(entity);
		assertNotNull(employeeServiceImpl.register(request, "123"));
	}
	
	@Test
	public void testRegisterForNullObj() throws Exception {
		Mockito.when(employeeServiceValidator.validateRequestParams(Mockito.any(), Mockito.any())).thenReturn(null);
		Mockito.when(employeePersistanceService.saveEntity(Mockito.any())).thenReturn(null);
		assertNotNull(employeeServiceImpl.register(request, "123"));
	}
	
	@Test
	public void testRegisterForExeption() throws Exception {
		Mockito.when(employeeServiceValidator.validateRequestParams(Mockito.any(), Mockito.any())).thenReturn(null);
		Mockito.when(employeePersistanceService.saveEntity(Mockito.any())).thenThrow(new EmployeeException());
		employeeServiceImpl.register(request, "123");
		assertThatExceptionOfType(EmployeeException.class);
	}
	
}
