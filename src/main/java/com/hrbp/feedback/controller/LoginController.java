package com.hrbp.feedback.controller;

/*
 * @author Shrinivas
 * @version 1.0
 * @since 2024-01-22
 */


import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.hrbp.feedback.model.dto.EmployeeDto;
import com.hrbp.feedback.exceptions.ResourceNotFoundException;
import com.hrbp.feedback.service.EmployeeService;
import jakarta.persistence.EntityManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.*;

@Slf4j
@RestController
@RequestMapping("/api/login")
@CrossOrigin("*")
public class LoginController {

    ObjectMapper objectMapper = new ObjectMapper();
    ObjectWriter objectWriter = objectMapper.writerWithDefaultPrettyPrinter();


    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private EntityManager entityManager;


    @PostMapping("/fetch")
    public ResponseEntity<?> fetchEmployeeByNameAndRole(@RequestBody Integer employeeID) {
        Optional<EmployeeDto> employeeDtoOptional = employeeService.findEmployeeDtoById(employeeID);

        if (employeeDtoOptional.isPresent()) {
            EmployeeDto employeeDto = employeeDtoOptional.get();
            Optional<List<EmployeeDto>> dashboardRecords = employeeService.getRecordsForDashboard(employeeID);
            Map<String , Object> response = new HashMap<>();
            response.put("employee", employeeDto);
            response.put("dashboardRecord",dashboardRecords.orElse(Collections.emptyList()) );
            log.info("Employee Data &  his Dashboard dats is successfully fetched");
            return ResponseEntity.ok(response);
        } else {
            throw new ResourceNotFoundException("No Employee Exists with ID : " + employeeID + " , try with existing employee!"); // Use a suitable error code
        }
    }


//    @GetMapping("/dashboard/{employeeId}")
    public Optional<List<EmployeeDto>> getRecordsForDashboard(@PathVariable Integer employeeId) {
        return employeeService.getRecordsForDashboard(employeeId);
    }







}
