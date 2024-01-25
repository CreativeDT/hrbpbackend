package com.hrbp.feedback.model.mapper;

import com.hrbp.feedback.model.dto.FeedbackDto;
import com.hrbp.feedback.model.entity.Feedback;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring", config = BaseMapperConfig.class)
public interface FeedbackMapper {
    FeedbackDto toDto(Feedback feedback);
    Feedback toEntity(FeedbackDto feedbackDto);

    List<FeedbackDto> toDTOList(List<Feedback> feedbackList);
}
