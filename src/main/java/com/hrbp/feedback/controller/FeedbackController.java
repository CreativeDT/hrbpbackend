package com.hrbp.feedback.controller;

import com.hrbp.feedback.model.dto.EmployeeDTO;
import com.hrbp.feedback.model.dto.FeedbackDTO;
import com.hrbp.feedback.model.dto.RoleDTO;
import com.hrbp.feedback.model.entity.Employee;
import com.hrbp.feedback.model.entity.Feedback;
import com.hrbp.feedback.model.entity.Role;
import com.hrbp.feedback.exceptions.ResourceNotFoundException;
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
@RequestMapping("/api/feedback")
@Slf4j
@CrossOrigin("*")
public class FeedbackController {

	@Autowired
	private FeedbackService feedbackService;

	@Autowired
	private EmployeeService employeeService;

	@PostMapping("createfeedback")
	public ResponseEntity<?> createFeedback(@RequestBody FeedbackDTO feedbackDto) {
		int employeeId = feedbackDto.getCreatorId();
		String userRole = getRoleFromEmployeeId(employeeId);

		if (!"HRBP".equals(userRole)) {
			log.warn("FORBID!!, Someone else with employee id: " + employeeId + " and role: " + userRole
					+ ", tried to create/update the feedback");
			return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Only HRBPs can create or update feedback.");
		}
		try {
			FeedbackDTO savedFeedback = feedbackService.createFeedback(feedbackDto);
			log.info("Created feedback, " + savedFeedback);
			return ResponseEntity.ok(savedFeedback);

		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
		}

	}

	@PutMapping("updatefeedback")
	public ResponseEntity<?> updateFeedback(@RequestBody FeedbackDTO feedbackDto) {
		log.info("updateFeedback(-) started");
		int employeeId = feedbackDto.getCreatorId();
		String userRole = getRoleFromEmployeeId(employeeId);
		log.info("userrole :" + userRole);
		try {
			FeedbackDTO savedFeedback = feedbackService.updateFeedback(feedbackDto,userRole);
			log.info("updateFeedback(-) completed");
			return ResponseEntity.ok(savedFeedback);

		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
		}

	}

	@GetMapping("allfeedbacks")
	public ResponseEntity<List<FeedbackDTO>> getAllFeedbacks() {
		log.info("getAllFeedbacks(-) started");
		List<FeedbackDTO> allFeedbacks = feedbackService.getAllFeedbacks();
		log.info("getAllFeedbacks(-) completed");
		return ResponseEntity.ok(allFeedbacks);
	}
	
	@GetMapping("/feedback/{employeeId}")
	public ResponseEntity<List<FeedbackDTO>> getFeedbacks(@PathVariable Integer employeeId) {
	    log.info("getFeedbacks(-) started :" + employeeId);
	    List<FeedbackDTO> feedbacks = feedbackService.getFeedbacks(employeeId);
	    log.info("getFeedbacks(-) completed");
	    return ResponseEntity.ok(feedbacks);
	}
	
	private String getRoleFromEmployeeId(int employeeId) {
		Optional<EmployeeDTO> employeeDto = employeeService.findEmployeeDtoById(employeeId);
		if (employeeDto.isEmpty()) {
			throw new ResourceNotFoundException("Employee not found with ID: " + employeeId);
		}
		RoleDTO roleDto = employeeDto.get().getRole();
		return roleDto.getRoleName();
	}

}
