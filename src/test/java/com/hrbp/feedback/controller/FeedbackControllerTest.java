package com.hrbp.feedback.controller;

import com.hrbp.feedback.model.dto.FeedbackDTO;
import com.hrbp.feedback.service.FeedbackService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class FeedbackControllerTest {

    @InjectMocks
    private  FeedbackController feedbackController;

    @Mock
    private FeedbackService feedbackService;


    @Test
    void getAllFeedbacks_ShouldReturnOkResponseWithFeedbackDtos(){
        // Arrange
        List<FeedbackDTO> expectedFeedbackDtos = List.of(new FeedbackDTO(), new FeedbackDTO());
        when(feedbackService.getAllFeedbacks()).thenReturn(expectedFeedbackDtos);

        // Act
        ResponseEntity<List<FeedbackDTO>> response = feedbackController.getAllFeedbacks();

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedFeedbackDtos, response.getBody());
    }

}
