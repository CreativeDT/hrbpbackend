package com.hrbp.feedback.controller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.when;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import com.hrbp.feedback.model.dto.FeedbackDTO;
import com.hrbp.feedback.service.FeedbackService;

@SpringBootTest
@AutoConfigureMockMvc
class FeedbackControllerTest {



	@MockBean
	private FeedbackService feedbackService;

	@InjectMocks
	private FeedbackController controller;

	@Test
	void testGetAllFeedbacks(){
		// Arrange
		Integer employeeId = 1;
		
		FeedbackDTO feedbackDTO = new FeedbackDTO();
		feedbackDTO.setEmployeeId(1);
		feedbackDTO.setStatus("Active");

		FeedbackDTO feedbackDTO1 = new FeedbackDTO();
		feedbackDTO1.setEmployeeId(2);
		feedbackDTO1.setStatus("InActive");

		List<FeedbackDTO> mockFeedbacks = Arrays.asList(feedbackDTO, feedbackDTO1);
//		when(feedbackService.getAllFeedbacks()).thenReturn(mockFeedbacks);
//		when(controller.getAllFeedbacks()).then(mockFeedbacks);

//		Integer employeeId = 2;
		when(feedbackService.getAllFeedbacks()).thenReturn(mockFeedbacks);
		ResponseEntity<List<FeedbackDTO>> responseEntity = controller.getAllFeedbacks();
		assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
		assertNull(responseEntity.getBody());

		// Assert
//		String content = result.getResponse().getContentAsString();
//		assertNotNull(content);

	}

}
