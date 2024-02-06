package com.hrbp.feedback.service;

import com.hrbp.feedback.config.HRBPConstants;
import com.hrbp.feedback.model.dto.FeedbackDTO;
import com.hrbp.feedback.model.entity.Employee;
import com.hrbp.feedback.model.entity.Feedback;
import com.hrbp.feedback.model.mapper.FeedbackMapper;
import com.hrbp.feedback.repository.EmployeeRepository;
import com.hrbp.feedback.repository.FeedbackRepository;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.*;

@Service
@Slf4j
public class FeedbackService {

	@Autowired
	private FeedbackRepository feedbackRepository;

	@Autowired
	private EmployeeRepository employeeRepository;

	@Autowired
	private FeedbackMapper feedbackMapper;

	public FeedbackDTO createFeedback(FeedbackDTO feedbackDto) {
		log.info("createFeedback(-) started Ticket ID is :"+feedbackDto.getTicketId());
		LocalDateTime now = LocalDateTime.now(); // Get current date and time
		Employee manager = employeeRepository.findById(feedbackDto.getCreatorId())
				.orElseThrow(() -> new RuntimeException("Employee not found")).getManager();

		int managerId = manager.getEmployeeId();
		Feedback feedback = feedbackMapper.toEntity(feedbackDto);
		if (feedback.getTicketId() == null || feedback.getTicketId() == 0) {
			feedback.setDateCreated(now);
			feedback.setAssignedManagerId(managerId);
			feedback.setTicketId(feedbackDto.getTicketId());
			feedbackMapper.toDto(feedbackRepository.save(feedback));
		}

		log.info("createFeedback(-) completed");
		return feedbackDto;

	}

	public FeedbackDTO updateFeedback(FeedbackDTO feedbackDto, String userRole) {
		log.info("updateFeedback(-) started");
		LocalDateTime now = LocalDateTime.now(); // Get current date and time

		Feedback feedback = feedbackMapper.toEntity(feedbackDto);
		if (userRole.equalsIgnoreCase(HRBPConstants.MANGER) || userRole.equalsIgnoreCase(HRBPConstants.BUHEAD)) {
			Optional<Feedback> existingFeedback = feedbackRepository.findById(feedback.getTicketId());
			if (existingFeedback.isPresent()) {
				Feedback updatedFeedback = existingFeedback.get();
				updatedFeedback.setConcerns(feedback.getConcerns());
				updatedFeedback.setCreatorId(feedback.getCreatorId());
				updatedFeedback.setAssignedManagerId(feedback.getAssignedManagerId());
				updatedFeedback.setExpert(feedback.getExpert());
				updatedFeedback.setStatus(feedback.getStatus());
				updatedFeedback.setLastStatusChangeDate(now);
				updatedFeedback.setEmployeeId(feedback.getEmployeeId());
				return feedbackMapper.toDto(feedbackRepository.save(updatedFeedback));
			}
		}
		log.info("updateFeedback(-) completed");
		return feedbackDto;

	}

	public List<FeedbackDTO> getAllFeedbacks() {
		List<Feedback> feedbackList = feedbackRepository.findAll();
		return feedbackMapper.toDTOList(feedbackList);
	}

	public List<FeedbackDTO> getFeedbacks(Integer employeeId) {
		log.info("getFeedback(-) started");
		List<Feedback> feedbacks = feedbackRepository.findByEmployeeId(employeeId);
		return feedbackMapper.toDTOList(feedbacks);
	}

}
