package com.hrbp.feedback.controller;

import com.hrbp.feedback.model.dto.EmployeeDTO;
import com.hrbp.feedback.model.dto.EmployeeReponse;
import com.hrbp.feedback.exceptions.EmployeeError;
import com.hrbp.feedback.exceptions.EmployeeNotFoundException;
import com.hrbp.feedback.service.EmployeeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api")
@Slf4j
@CrossOrigin("*")
public class EmployeeController {

	@Autowired
	private EmployeeService employeeService;

	@GetMapping("/employeeDetails/{employeeId}")
	public ResponseEntity<EmployeeReponse> findEmployeeDetails(@PathVariable Integer employeeId) {
		log.info("findEmployeeDetails started");
		Optional<EmployeeDTO> employeeDtoOptional = employeeService.findEmployeeDtoById(employeeId);

		if (employeeDtoOptional.isPresent()) {
			EmployeeDTO employeeDto = employeeDtoOptional.get();
			log.info("findEmployeeDetails completed");
			return ResponseEntity.ok(new EmployeeReponse(employeeDto, null));
		} else {
			// Handle the case where no employee with the specified ID is found
			log.error("No Employee Exists with ID: {}, try with an existing employee!", employeeId);

			EmployeeError employeeError = new EmployeeError();
			employeeError.setErrorType("Not Found");
			employeeError.setErrorMessage("No employee found with ID: " + employeeId);

			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new EmployeeReponse(null, employeeError));
		}
	}

	@GetMapping("/dashboardDetails/{employeeId}")
	public ResponseEntity<List<EmployeeDTO>> getRecordsForDashboard(@PathVariable Integer employeeId) {
		log.info("getRecordsForDashboard(-) started");
		Optional<List<EmployeeDTO>> dashboardRecordsOptional = employeeService.getRecordsForDashboard(employeeId);

		if (dashboardRecordsOptional.isPresent()) {
			// Employee ID exists, return the dashboard records
			List<EmployeeDTO> dashboardRecords = dashboardRecordsOptional.get();
			log.info("Dashboard records successfully fetched");
			return ResponseEntity.ok(dashboardRecords);
		} else {
			// Handle the case where no employee with the specified ID is found
			log.error("No Employee Exists with ID: " + employeeId + ", try with an existing employee!");
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
	}

}
