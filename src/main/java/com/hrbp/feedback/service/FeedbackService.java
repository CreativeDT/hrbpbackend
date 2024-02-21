package com.hrbp.feedback.service;

import com.hrbp.feedback.config.HRBPConstants;
import com.hrbp.feedback.exceptions.EmployeeNotFoundException;
import com.hrbp.feedback.model.dto.ActionItemDto;
import com.hrbp.feedback.model.dto.FeedbackDTO;
import com.hrbp.feedback.model.entity.ActionItem;
import com.hrbp.feedback.model.entity.Employee;
import com.hrbp.feedback.model.entity.Feedback;
import com.hrbp.feedback.model.mapper.ActionItemMapper;
import com.hrbp.feedback.model.mapper.FeedbackMapper;
import com.hrbp.feedback.repository.ActionItemRepository;
import com.hrbp.feedback.repository.EmployeeRepository;
import com.hrbp.feedback.repository.FeedbackRepository;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
public class FeedbackService {

	@Autowired
	private FeedbackRepository feedbackRepository;

	@Autowired
	private EmployeeRepository employeeRepository;

	@Autowired
	private FeedbackMapper feedbackMapper;

	@Autowired
	private ActionItemRepository actionItemRepository;

	@Autowired
	private ActionItemMapper actionItemMapper;

	@Autowired
	private Feedback feedback;

	public FeedbackDTO createFeedback(FeedbackDTO feedbackDto) {
		log.info("createFeedback(-) started Ticket ID is :" + feedbackDto.getTicketId());
		log.info(dateTimeFormat());
		Employee manager = employeeRepository.findById(feedbackDto.getCreatorId())
				.orElseThrow(() -> new EmployeeNotFoundException("Employee not found"));

		feedback = feedbackMapper.toEntity(feedbackDto);
		if (feedback.getTicketId() == null || feedback.getTicketId() == 0) {
			feedback.setCreatedDate(dateTimeFormat());
			// Save the Feedback entity
			feedback = feedbackRepository.save(feedback);
			List<ActionItem> actionItems = null;
			if (feedbackDto.getActionItems() != null && !feedbackDto.getActionItems().isEmpty()) {
				log.info("Save associated ActionItem entities");
				actionItems = feedbackDto.getActionItems().stream().map(actionItemDto -> {
					ActionItem actionItem = actionItemMapper.toEntity(actionItemDto);
					log.info("Set the association with the Feedback entity");
					actionItem.setFeedback(feedback);
					return actionItem;
				}).collect(Collectors.toList());

				log.info(" Save the associated ActionItem entities");
				actionItemRepository.saveAll(actionItems);
				feedbackDto.setActionItems(actionItemMapper.toDTOList(actionItems));
			}
			feedbackDto = feedbackMapper.toDto(feedback);
		}

		log.info("createFeedback(-) completed");
		return feedbackDto;
	}

	public FeedbackDTO updateFeedback(FeedbackDTO feedbackDto, String userRole) {
		log.info("updateFeedback(-) started");
		Feedback updatedFeedback = null;

		if (userRole.equalsIgnoreCase(HRBPConstants.MANGER) || userRole.equalsIgnoreCase(HRBPConstants.BUHEAD)) {
			Optional<Feedback> existingFeedback = feedbackRepository.findById(feedbackDto.getTicketId());
			
			if (existingFeedback.isPresent()) {
				log.info("Existing Feedback Present");
				updatedFeedback = existingFeedback.get();
				updatedFeedback.setConcerns(feedbackDto.getConcerns());
				updatedFeedback.setCreatorId(feedbackDto.getCreatorId());
				updatedFeedback.setAssignedManagerId(feedbackDto.getAssignedManagerId());
				updatedFeedback.setExpert(feedbackDto.getExpert());
				updatedFeedback.setStatus(feedbackDto.getStatus());
				updatedFeedback.setUpdatedDate(dateTimeFormat());
				updatedFeedback.setEmployeeId(feedbackDto.getEmployeeId());
				// Add or update action items
				List<ActionItem> existingActionItems = updatedFeedback.getActionItems();
				List<ActionItemDto> newActionItems = feedbackDto.getActionItems();

				if (existingActionItems == null) {
					existingActionItems = new ArrayList<>();
				}
				// Update existing action items
				for (ActionItemDto newActionItemDto : newActionItems) {
					Optional<ActionItem> existingActionItem = existingActionItems.stream()
							.filter(actionItem -> actionItem.getId().equals(newActionItemDto.getId())).findFirst();
					if (existingActionItem.isPresent()) {
						ActionItem actionItem = existingActionItem.get();
						actionItem.setDescription(newActionItemDto.getDescription());
						actionItem.setRAG(newActionItemDto.getRAG());
						actionItem.setActionOwner(newActionItemDto.getActionOwner());
						actionItem.setStatus(newActionItemDto.getStatus());
					}
					feedbackDto.setUpdatedDate(dateTimeFormat());
					feedbackDto.setActionItems(actionItemMapper.toDTOList(existingActionItems));
				}

				// Save the updated feedback entity
				Feedback feedback = feedbackRepository.save(updatedFeedback);
				// Save new action items
				List<ActionItem> newActionItemsEntities = newActionItems.stream().map(actionItemMapper::toEntity)
						.peek(actionItem -> actionItem.setFeedback(feedback)).collect(Collectors.toList());
				actionItemRepository.saveAll(newActionItemsEntities);
				// Set the saved ActionItem entities in the FeedbackDTO
				feedbackDto.setActionItems(actionItemMapper.toDTOList(newActionItemsEntities));
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

	private String dateTimeFormat() {
		log.info("dateTimeFormat(-)");
		LocalDateTime now = LocalDateTime.now();
		// Define a formatter for "yyyy-MM-dd HH:mm"
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
		// Format and print the LocalDateTime
		return now.format(formatter);

	}

}
