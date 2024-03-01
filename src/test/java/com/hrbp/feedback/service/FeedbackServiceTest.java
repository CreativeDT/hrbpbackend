package com.hrbp.feedback.service;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import com.hrbp.feedback.config.HRBPConstants;
import com.hrbp.feedback.model.dto.ActionItemDto;
import com.hrbp.feedback.model.dto.FeedbackDTO;
import com.hrbp.feedback.model.entity.ActionItem;
import com.hrbp.feedback.model.entity.Feedback;
import com.hrbp.feedback.model.mapper.ActionItemMapper;
import com.hrbp.feedback.model.mapper.FeedbackMapper;
import com.hrbp.feedback.repository.ActionItemRepository;
import com.hrbp.feedback.repository.FeedbackRepository;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.CoreMatchers.any;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
class FeedbackServiceTest {

	@Mock
	private FeedbackRepository feedbackRepository;

	@Mock
	private FeedbackMapper feedbackMapper;

	@InjectMocks
	private FeedbackService feedbackService;

	@Mock
	private ActionItemRepository actionItemRepository;

	@Mock
	private ActionItemMapper actionItemMapper;

	@Test
	void testGetFeedbacks() {
		// Mock data
		Integer employeeId = 1;

		Feedback feedback1 = new Feedback();

		feedback1.setCreatorId(5);
		feedback1.setTicketId(101);
		feedback1.setAssignedManagerId(3);
		feedback1.setConcerns(null);
		feedback1.setEmployeeId(12364);

		Feedback feedback2 = new Feedback();

		feedback2.setCreatorId(5);
		feedback2.setTicketId(102);
		feedback2.setAssignedManagerId(3);
		feedback2.setConcerns(null);
		feedback2.setEmployeeId(12364);

		List<Feedback> mockFeedbackList = Arrays.asList(feedback1, feedback2);

		FeedbackDTO feedbackDTO1 = new FeedbackDTO();

		feedbackDTO1.setCreatorId(5);
		feedbackDTO1.setTicketId(101);
		feedbackDTO1.setAssignedManagerId(3);
		feedbackDTO1.setConcerns(null);

		FeedbackDTO feedbackDTO2 = new FeedbackDTO();
		feedbackDTO2.setCreatorId(5);
		feedbackDTO2.setTicketId(101);
		feedbackDTO2.setAssignedManagerId(3);
		feedbackDTO2.setConcerns(null);

		List<FeedbackDTO> expectedDTOList = Arrays.asList(feedbackDTO1, feedbackDTO2);

		// Mock behavior
		when(feedbackRepository.findByEmployeeId(employeeId)).thenReturn(mockFeedbackList);
		when(feedbackMapper.toDTOList(mockFeedbackList)).thenReturn(expectedDTOList);

		// Call the method under test
		List<FeedbackDTO> result = feedbackService.getFeedbacks(employeeId);

		// Verify the result
		assertEquals(expectedDTOList.size(), result.size());

		assertEquals(expectedDTOList.get(0).getEmployeeId(), result.get(0).getEmployeeId());
		// Add more assertions based on your specific logic

		// Verify that the repository method was called with the correct argument
		verify(feedbackRepository, times(1)).findByEmployeeId(employeeId);
		// Verify that the mapper method was called with the correct argument
		verify(feedbackMapper, times(1)).toDTOList(mockFeedbackList);
	}

	@Test
	public void testGetAllFeedbacks() {
		// Arrange

		Integer employeeId = 1;

		Feedback feedback1 = new Feedback();

		feedback1.setCreatorId(5);
		feedback1.setTicketId(101);
		feedback1.setAssignedManagerId(3);
		feedback1.setConcerns(null);
		feedback1.setEmployeeId(12364);

		FeedbackDTO feedbackDTO1 = new FeedbackDTO();

		feedbackDTO1.setCreatorId(5);
		feedbackDTO1.setTicketId(101);
		feedbackDTO1.setAssignedManagerId(3);
		feedbackDTO1.setConcerns(null);
		List<Feedback> mockFeedbackList = List.of(feedback1);
		List<FeedbackDTO> mockFeedbackDTOList = List.of(feedbackDTO1);

		when(feedbackRepository.findAll()).thenReturn(mockFeedbackList);
		when(feedbackMapper.toDTOList(mockFeedbackList)).thenReturn(mockFeedbackDTOList);

		// Act
		List<FeedbackDTO> result = feedbackService.getAllFeedbacks();

		// Assert
		assertEquals(mockFeedbackDTOList, result);
	}

	@Test
    public void testGetAllFeedbacks_EmptyList() {
        // Arrange
        when(feedbackRepository.findAll()).thenReturn(List.of());
        when(feedbackMapper.toDTOList(List.of())).thenReturn(List.of());

        // Act
        List<FeedbackDTO> result = feedbackService.getAllFeedbacks();

        // Assert
        assertTrue(result.isEmpty());
    }

}
