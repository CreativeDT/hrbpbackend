package com.hrbp.feedback.controller;


import com.hrbp.feedback.model.dto.EmployeeDTO;
import com.hrbp.feedback.exceptions.ResourceNotFoundException;
import com.hrbp.feedback.service.EmployeeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.*;

@RestController
@RequestMapping("/api/login")
@Slf4j
@CrossOrigin("*")
public class LoginController {

    @Autowired
    private EmployeeService employeeService;

    @GetMapping("/findemployeedetails/{employeeId}")
    public ResponseEntity<EmployeeDTO> findemployeedetails(@PathVariable Integer employeeId) {
    	log.info("fetchEmployeeByNameAndRole started");
        Optional<EmployeeDTO> employeeDtoOptional = employeeService.findEmployeeDtoById(employeeId);

        if (employeeDtoOptional.isPresent()) {
            EmployeeDTO employeeDto = employeeDtoOptional.get();
            log.info("Employee Data &  his Dashboard dats is successfully fetched");
            log.info("fetchEmployeeByNameAndRole completed");
            return ResponseEntity.ok(employeeDto);
        } else {
            throw new ResourceNotFoundException("No Employee Exists with ID : " + employeeId + " , try with existing employee!"); // Use a suitable error code
        }
    }


  

}
