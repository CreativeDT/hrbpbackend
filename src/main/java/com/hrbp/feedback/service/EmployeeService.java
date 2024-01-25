package com.hrbp.feedback.service;


import com.hrbp.feedback.model.dto.EmployeeDto;
import com.hrbp.feedback.model.dto.FeedbackDto;
import com.hrbp.feedback.model.entity.Employee;
import com.hrbp.feedback.exceptions.ResourceNotFoundException;
import com.hrbp.feedback.model.entity.Feedback;
import com.hrbp.feedback.model.mapper.EmployeeMapper;
import com.hrbp.feedback.model.mapper.FeedbackMapper;
import com.hrbp.feedback.repository.EmployeeRepository;
import com.hrbp.feedback.repository.FeedbackRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

@Service
@Transactional
public class EmployeeService {


    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private FeedbackRepository feedbackRepository;

    @Autowired
    private EmployeeMapper employeeMapper;

    @Autowired
    private FeedbackMapper feedbackMapper;


    public Optional<EmployeeDto> findEmployeeDtoById(Integer employeeId) {
        Optional<Employee> employee = employeeRepository.findById(employeeId);
        return employee.map(employeeMapper::toDto);
    }


    public Optional<List<EmployeeDto>> getRecordsForDashboard(Integer employeeId) {
        Optional<Employee> employee = employeeRepository.findById(employeeId);
        if (employee.isPresent()) {
            String roleName = employee.get().getRole().getRoleName();
            return switch (roleName) {
                case "Bu_head": // Combine cases for similar logic
                    yield Optional.of(employeeRepository.findEmployeesByBuHeadId(employeeId) // Wrap in Optional.of
                            .stream()
                            .map(employeeMapper::toDto)
                            .peek(employeeDto -> {
                                if (employeeDto.getRole().getRoleName().equals("HRBP") ||
                                        employeeDto.getRole().getRoleName().equals("Manager")) {
                                    fetchFeedbacksIfHrbp(employeeDto);
                                }
                            }).toList());
                case "Manager":
                    yield Optional.of(employeeRepository.findEmployeesByManagerId(employeeId)
                            .stream().map(employeeMapper::toDto).peek(this::fetchFeedbacksIfHrbp).toList());
                case "HRBP":
                    EmployeeDto employeeDto = employeeMapper.toDto(employee.orElseThrow());
                    employeeDto.setFeedbacks(feedbackRepository.findByCreatorId(employeeId));
                    yield Optional.of(List.of(employeeDto)); // Already wrapped in Optional.of
                default:
                    throw new IllegalArgumentException("Invalid role: " + roleName);
            };
        } else {
            throw new ResourceNotFoundException("No Employee Exists with ID : " + employeeId + " , try with existing employee!");
        }
    }

    private void fetchFeedbacksIfHrbp(EmployeeDto employeeDto) {
        if (employeeDto.getRole().getRoleName().equals("HRBP") ||
                employeeDto.getRole().getRoleName().equals("manager")) {
            employeeDto.setFeedbacks(feedbackRepository.findByCreatorId(employeeDto.getEmployeeId()));
        }
    }

}