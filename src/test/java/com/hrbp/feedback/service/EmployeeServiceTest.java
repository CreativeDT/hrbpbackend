package com.hrbp.feedback.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import com.hrbp.feedback.model.dto.EmployeeDTO;
import com.hrbp.feedback.model.dto.RoleDTO;
import com.hrbp.feedback.model.entity.Employee;
import com.hrbp.feedback.model.entity.Feedback;
import com.hrbp.feedback.model.mapper.EmployeeMapper;
import com.hrbp.feedback.repository.EmployeeRepository;
import com.hrbp.feedback.repository.FeedbackRepository;

@SpringBootTest
class EmployeeServiceTest {

	@Mock
	private EmployeeRepository employeeRepository;

	@Mock
	private FeedbackRepository feedbackRepository;

	@InjectMocks
	private EmployeeService employeeService;

	@Mock
	private EmployeeMapper employeeMapper;

	@Test
	void testFindEmployeeDtoById() {

		// Mock data
		Employee mockEmployee = new Employee();
		mockEmployee.setEmployeeId(1);
		mockEmployee.setFirstName("Test");
		EmployeeDTO mockEmployeeDTO = new EmployeeDTO();
		mockEmployeeDTO.setEmployeeId(1);
		mockEmployeeDTO.setFirstName("Test");
		when(employeeRepository.findById(1)).thenReturn(Optional.of(mockEmployee));
		when(employeeMapper.toDto(mockEmployee)).thenReturn(mockEmployeeDTO);
		// Call the method under test
		Optional<EmployeeDTO> result = employeeService.findEmployeeDtoById(1);
		// Verify the result
		assertTrue(result.isPresent());
		assertEquals(mockEmployeeDTO.getEmployeeId(), result.get().getEmployeeId());
		// Verify that the repository method was called with the correct argument
		verify(employeeRepository, times(1)).findById(1);
		// Verify that the mapper method was called with the correct argument
		verify(employeeMapper, times(1)).toDto(mockEmployee);
	}

	@Test
	void testFetchFeedbacksIfHrbp() {
		// Mock data
		EmployeeDTO mockEmployeeDTO = new EmployeeDTO();
		mockEmployeeDTO.setRole(new RoleDTO(100, "HRBP")); 
		Feedback mockFeedback1 = new Feedback();
		mockFeedback1.setEmployeeId(1);
		mockFeedback1.setCreatorId(mockEmployeeDTO.getEmployeeId());
		// Add more mock feedbacks as needed
		List<Feedback> mockFeedbackList = Arrays.asList(mockFeedback1);
		// Mock behavior
		when(feedbackRepository.findByCreatorId(mockEmployeeDTO.getEmployeeId())).thenReturn(mockFeedbackList);
		// Call the method under test
		employeeService.fetchFeedbacksIfHrbp(mockEmployeeDTO);
		// Verify the result
		assertEquals(mockFeedbackList.get(0).getEmployeeId(), mockEmployeeDTO.getFeedbacks().get(0).getEmployeeId());
		// Verify that the repository method was called with the correct argument
		verify(feedbackRepository, times(1)).findByCreatorId(mockEmployeeDTO.getEmployeeId());
	}

}
