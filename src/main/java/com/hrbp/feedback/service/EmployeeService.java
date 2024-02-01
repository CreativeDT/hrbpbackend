package com.hrbp.feedback.service;

import com.hrbp.feedback.model.dto.EmployeeDTO;
import com.hrbp.feedback.model.entity.Employee;
import com.hrbp.feedback.config.Constants;
import com.hrbp.feedback.exceptions.ResourceNotFoundException;
import com.hrbp.feedback.model.mapper.EmployeeMapper;
import com.hrbp.feedback.model.mapper.FeedbackMapper;
import com.hrbp.feedback.repository.EmployeeRepository;
import com.hrbp.feedback.repository.FeedbackRepository;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class EmployeeService {

	@Autowired
	private EmployeeRepository employeeRepository;

	@Autowired
	private FeedbackRepository feedbackRepository;

	@Autowired
	private EmployeeMapper employeeMapper;

	@Autowired
	private FeedbackMapper feedbackMapper;
	
	

	public Optional<EmployeeDTO> findEmployeeDtoById(Integer employeeId) {
		Optional<Employee> employee = employeeRepository.findById(employeeId);
		return employee.map(employeeMapper::toDto);
	}

	public Optional<List<EmployeeDTO>> getRecordsForDashboard(Integer employeeId) {
		log.info("getRecordsForDashboard(-) started");
		Optional<Employee> employee = employeeRepository.findById(employeeId);
		if (employee.isPresent()) {
			String roleName = employee.get().getRole().getRoleName();
			return switch (roleName) {
			case Constants.Bu_head: // Combine cases for similar logic
				yield Optional.of(employeeRepository.findEmployeesByBuHeadId(employeeId) // Wrap in Optional.of
						.stream().map(employeeMapper::toDto).peek(employeeDto -> {
							if (employeeDto.getRole().getRoleName().equals(Constants.HRBP)
									|| employeeDto.getRole().getRoleName().equals(Constants.Manager)) {
								fetchFeedbacksIfHrbp(employeeDto);
							}
						}).toList());
			case Constants.Manager:
				yield Optional.of(employeeRepository.findEmployeesByManagerId(employeeId).stream()
						.map(employeeMapper::toDto).peek(this::fetchFeedbacksIfHrbp).toList());
			case Constants.HRBP:
				EmployeeDTO employeeDto = employeeMapper.toDto(employee.orElseThrow());
				employeeDto.setFeedbacks(feedbackRepository.findByCreatorId(employeeId));
				yield Optional.of(List.of(employeeDto)); // Already wrapped in Optional.of
			default:
				throw new IllegalArgumentException("Invalid role: " + roleName);
			};
		} else {
			throw new ResourceNotFoundException(
					"No Employee Exists with ID : " + employeeId + " , try with existing employee!");
		}
	}

	private void fetchFeedbacksIfHrbp(EmployeeDTO employeeDto) {
		if (employeeDto.getRole().getRoleName().equals(Constants.HRBP)
				|| employeeDto.getRole().getRoleName().equals(Constants.Manager)) {
			employeeDto.setFeedbacks(feedbackRepository.findByCreatorId(employeeDto.getEmployeeId()));
		}
	}

	
	
}