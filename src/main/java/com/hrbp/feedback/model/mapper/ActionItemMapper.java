package com.hrbp.feedback.model.mapper;

import com.hrbp.feedback.model.dto.ActionItemDto;
import com.hrbp.feedback.model.dto.FeedbackDTO;
import com.hrbp.feedback.model.entity.ActionItem;
import com.hrbp.feedback.model.entity.Feedback;

import java.util.List;

import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", config = BaseMapperConfig.class)
public interface ActionItemMapper {

	ActionItemDto toDto(ActionItem actionItem);

	ActionItem toEntity(ActionItemDto actionItemDto);
	
	List<ActionItemDto> toDTOList(List<ActionItem> actionItemlist);
}
