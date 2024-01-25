package com.hrbp.feedback.controller;
/*
 * @author Shrinivas
 * @version 1.0
 * @since 2024-01-22
 */

import com.hrbp.feedback.model.dto.EmployeeDto;
import com.hrbp.feedback.model.dto.FeedbackDto;
import com.hrbp.feedback.model.dto.RoleDto;
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
public class FeedbackController {

    @Autowired
    private FeedbackService feedbackService;

    @Autowired
    private EmployeeService employeeService;

    @PostMapping("create-update")
    public ResponseEntity<?> createOrUpdateFeedback(@RequestBody FeedbackDto feedbackDto) {
        int employeeId = feedbackDto.getCreatorId();


        String userRole = getRoleFromEmployeeId(employeeId);

        if (!"HRBP".equals(userRole)) {
            log.warn("FORBID!!, Someone else with employee id: "+ employeeId+" and role: "+userRole+", tried to create/update the feedback");
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Only HRBPs can create or update feedback.");
        }
        try {
            FeedbackDto savedFeedback = feedbackService.createOrUpdateFeedback(feedbackDto);
            log.info("Created feedback, "+ savedFeedback);
            return ResponseEntity.ok(savedFeedback);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }

    }

    @GetMapping("all-feedbacks")
    public ResponseEntity<List<FeedbackDto>> getAllFeedbacks() {
        List<FeedbackDto> allFeedbacks = feedbackService.getAllFeedbacks();
        log.info("Feedbacks fetched successfully");
        return ResponseEntity.ok(allFeedbacks);
    }

    @DeleteMapping("/delete-feedback/{id}")
    public ResponseEntity<?> deleteFeedback(@PathVariable int id) {
        try {
            feedbackService.deleteFeedbackById(id);
            log.info("Feedback with id :"+id+" , deleted Successfully");
            return ResponseEntity.noContent().build(); // 204 No Content status code
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Feedback not found with ID: " + id);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }


    private String getRoleFromEmployeeId(int employeeId) {
        Optional<EmployeeDto> employeeDto = employeeService.findEmployeeDtoById(employeeId);
        if (employeeDto.isEmpty()) {
            throw new ResourceNotFoundException("Employee not found with ID: " + employeeId);
        }
        RoleDto roleDto = employeeDto.get().getRole();
        return roleDto.getRoleName();
    }


}


