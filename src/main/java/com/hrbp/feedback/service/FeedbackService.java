package com.hrbp.feedback.service;

import com.hrbp.feedback.model.dto.FeedbackDto;
import com.hrbp.feedback.model.entity.Employee;
import com.hrbp.feedback.model.entity.Feedback;
import com.hrbp.feedback.model.mapper.FeedbackMapper;
import com.hrbp.feedback.repository.EmployeeRepository;
import com.hrbp.feedback.repository.FeedbackRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.*;

@Service
@Transactional
public class FeedbackService {

    @Autowired
    private FeedbackRepository feedbackRepository;

    @Autowired
    private EmployeeRepository employeeRepository;


    @Autowired
    private FeedbackMapper feedbackMapper;

    public FeedbackDto createOrUpdateFeedback(FeedbackDto feedbackDto) {
        LocalDateTime now = LocalDateTime.now(); // Get current date and time
        Employee manager = employeeRepository.findById(feedbackDto.getCreatorId())
                .orElseThrow(() -> new RuntimeException("Employee not found"))
                .getManager();

        int managerId = manager.getEmployeeId();

        Feedback feedback = feedbackMapper.toEntity(feedbackDto);


        if (feedback.getTicketId() == null || feedback.getTicketId() == 0) {
            feedback.setDateCreated(now);
            feedback.setAssignedManagerId(managerId);


            return feedbackMapper.toDto(feedbackRepository.save(feedback));
        } else {
            Optional<Feedback> existingFeedback = feedbackRepository.findById(feedback.getTicketId());
            if (existingFeedback.isPresent()) {
                Feedback updatedFeedback = existingFeedback.get();
                updatedFeedback.setTicketDetails(feedback.getTicketDetails());
                updatedFeedback.setStatus(feedback.getStatus());
                updatedFeedback.setLastStatusChangeDate(now);
                return feedbackMapper.toDto(feedbackRepository.save(updatedFeedback));
            } else {
                feedback.setDateCreated(now);
                feedback.setAssignedManagerId(managerId);

                return feedbackMapper.toDto(feedbackRepository.save(feedback)) ; // Create new if ID not found
            }
        }

    }


    public List<FeedbackDto> getAllFeedbacks() {
        List<Feedback> feedbackList = feedbackRepository.findAll();
        return feedbackMapper.toDTOList(feedbackList);
    }

    public void deleteFeedbackById(int feedbackId) {
        feedbackRepository.deleteById(feedbackId);
    }


}
