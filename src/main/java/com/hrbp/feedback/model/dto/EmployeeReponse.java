package com.hrbp.feedback.model.dto;

import com.hrbp.feedback.exceptions.EmployeeError;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeReponse {
	
	private EmployeeDTO employeeDTO;
	private EmployeeError employeeError;

}
 