package com.hrbp.feedback.model.mapper;

import com.hrbp.feedback.model.dto.FeedbackDTO;
import com.hrbp.feedback.model.entity.Feedback;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring", config = BaseMapperConfig.class)
public interface FeedbackMapper {
	
	FeedbackDTO toDto(Feedback feedback);
	Feedback toEntity(FeedbackDTO feedbakDto);
	List<FeedbackDTO> toDTOList(List<Feedback> feedbackList);
}
