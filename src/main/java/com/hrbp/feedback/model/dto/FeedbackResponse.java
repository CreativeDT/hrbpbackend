package com.hrbp.feedback.model.dto;

import com.hrbp.feedback.exceptions.FeedbackError;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class FeedbackResponse {

	private FeedbackDTO feedbackDTO;
	private FeedbackError feedbackerror;
	
}
