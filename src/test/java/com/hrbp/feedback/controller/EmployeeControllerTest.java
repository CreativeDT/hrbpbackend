package com.hrbp.feedback.controller;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.hrbp.feedback.model.dto.EmployeeDTO;
import com.hrbp.feedback.model.dto.EmployeeReponse;
import com.hrbp.feedback.service.EmployeeService;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

//@WebMvcTest(EmployeeController.class)
@SpringBootTest
class EmployeeControllerTest {

	@Mock
	private EmployeeService employeeService;

	@InjectMocks
	private EmployeeController controller;

	@Test
	void testFindEmployeeDetailsWithExistingEmployee() {
		Integer employeeId = 1;
		EmployeeDTO mockEmployeeDTO = new EmployeeDTO();
		mockEmployeeDTO.setEmployeeId(1);
		mockEmployeeDTO.setFirstName("Akash");
		mockEmployeeDTO.setLastName("Patil");
		when(employeeService.findEmployeeDtoById(employeeId)).thenReturn(Optional.of(mockEmployeeDTO));
		ResponseEntity<EmployeeReponse> responseEntity = controller.findEmployeeDetails(employeeId);
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		assertEquals(mockEmployeeDTO.getFirstName(), responseEntity.getBody().getEmployeeDTO().getFirstName());
		assertEquals(mockEmployeeDTO.getLastName(), responseEntity.getBody().getEmployeeDTO().getLastName());
		assertNotNull(responseEntity.getBody());
	}

	@Test
	void testFindEmployeeDetailsWithNonExistingEmployee() {
		Integer employeeId = 2;
		when(employeeService.findEmployeeDtoById(employeeId)).thenReturn(Optional.empty());
		ResponseEntity<EmployeeReponse> responseEntity = controller.findEmployeeDetails(employeeId);
		assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
		assertNotNull(responseEntity.getBody());
	}

	@Test
	void testGetRecordsForDashboardWithExistingEmployee() {
		Integer employeeId = 1;
		EmployeeDTO mockEmployeeDTO = new EmployeeDTO();
		mockEmployeeDTO.setEmployeeId(1);
		mockEmployeeDTO.setFirstName("Akash");
		mockEmployeeDTO.setLastName("Patil");

		EmployeeDTO mockEmployeeDTO1 = new EmployeeDTO();
		mockEmployeeDTO1.setEmployeeId(2);
		mockEmployeeDTO1.setFirstName("Sourab");
		mockEmployeeDTO1.setLastName("Neel");

		List<EmployeeDTO> mockDashboardRecords = new ArrayList<>();
		mockDashboardRecords.add(mockEmployeeDTO);
		mockDashboardRecords.add(mockEmployeeDTO1);
		when(employeeService.getRecordsForDashboard(employeeId)).thenReturn(Optional.of(mockDashboardRecords));
		ResponseEntity<List<EmployeeDTO>> responseEntity = controller.getRecordsForDashboard(employeeId);
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		assertEquals(mockEmployeeDTO.getFirstName(), responseEntity.getBody().get(0).getFirstName());
		assertEquals(mockEmployeeDTO1.getFirstName(), responseEntity.getBody().get(1).getFirstName());
		assertNotNull(responseEntity.getBody());
	}

	@Test
	void testGetRecordsForDashboardWithNonExistingEmployee() {
		Integer employeeId = 2;
		when(employeeService.getRecordsForDashboard(employeeId)).thenReturn(Optional.empty());
		ResponseEntity<List<EmployeeDTO>> responseEntity = controller.getRecordsForDashboard(employeeId);
		assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
		assertNull(responseEntity.getBody());
	}

}
