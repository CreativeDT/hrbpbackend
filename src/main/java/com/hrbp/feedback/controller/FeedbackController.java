package com.hrbp.feedback.controller;

import com.hrbp.feedback.model.dto.EmployeeDTO;
import com.hrbp.feedback.model.dto.FeedbackDTO;
import com.hrbp.feedback.model.dto.FeedbackResponse;
import com.hrbp.feedback.model.dto.RoleDTO;
import com.hrbp.feedback.model.entity.Employee;
import com.hrbp.feedback.model.entity.Feedback;
import com.hrbp.feedback.model.entity.Role;
import com.hrbp.feedback.exceptions.FeedbackError;
import com.hrbp.feedback.exceptions.EmployeeNotFoundException;
import com.hrbp.feedback.service.EmployeeService;
import com.hrbp.feedback.service.FeedbackService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
@Slf4j
@CrossOrigin("*")
public class FeedbackController {

	@Autowired
	private FeedbackService feedbackService;

	@Autowired
	private EmployeeService employeeService;

	@PostMapping("/feedback")
	public ResponseEntity<FeedbackResponse> createFeedback(@RequestBody FeedbackDTO feedbackDto) {
		int employeeId = feedbackDto.getCreatorId();
		String userRole = getRoleFromEmployeeId(employeeId);
		log.info("user Role is :"+userRole);
		if (!"HRBP".equals(userRole)) {
			log.warn("FORBID!!, Someone else with employee id: " + employeeId + " and role: " + userRole
					+ ", tried to create/update the feedback");
			FeedbackError error = new FeedbackError("Forbidden", "Only HRBPs can create or update feedback.");
			return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new FeedbackResponse(null, error));
		}

		try {
			FeedbackDTO savedFeedback = feedbackService.createFeedback(feedbackDto);
			log.info("Created feedback, " + savedFeedback);
			return ResponseEntity.ok(new FeedbackResponse(savedFeedback, null));

		} catch (Exception e) {
			FeedbackError error = new FeedbackError("Internal Server Error", e.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new FeedbackResponse(null, error));
		}
	}

	@PutMapping("/feedback")
	public ResponseEntity<FeedbackResponse> updateFeedback(@RequestBody FeedbackDTO feedbackDto) {
		log.info("updateFeedback(-) started");
		int employeeId = feedbackDto.getCreatorId();
		String userRole = getRoleFromEmployeeId(employeeId);
		log.info("userRole: " + userRole);

		try {
			FeedbackDTO savedFeedback = feedbackService.updateFeedback(feedbackDto, userRole);
			log.info("updateFeedback(-) completed");
			return ResponseEntity.ok(new FeedbackResponse(savedFeedback, null));

		} catch (Exception e) {
			FeedbackError error = new FeedbackError("Internal Server Error", e.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new FeedbackResponse(null, error));
		}
	}

	@GetMapping("/feedback")
	public ResponseEntity<List<FeedbackDTO>> getAllFeedbacks() {
		log.info("getAllFeedbacks() started");
		List<FeedbackDTO> allFeedbacks = feedbackService.getAllFeedbacks();
		log.info("getAllFeedbacks() completed");
		return ResponseEntity.ok(allFeedbacks);
	}

	@GetMapping("/feedback/{employeeId}")
	public ResponseEntity<List<FeedbackDTO>> getFeedbacks(@PathVariable Integer employeeId) {
		log.info("getFeedbacks(-) started :" + employeeId);
		List<FeedbackDTO> feedbacks = feedbackService.getFeedbacks(employeeId);
		log.info("getFeedbacks(-) completed");
		return ResponseEntity.ok(feedbacks);
	}

	public String getRoleFromEmployeeId(int employeeId) {
		Optional<EmployeeDTO> employeeDto = employeeService.findEmployeeDtoById(employeeId);
		if (employeeDto.isEmpty()) {
			throw new EmployeeNotFoundException("Employee not found with ID: " + employeeId);
		}
		RoleDTO roleDto = employeeDto.get().getRole();
		return roleDto.getRoleName();
	}

}
