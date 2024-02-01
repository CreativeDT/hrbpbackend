package com.hrbp.feedback.model.mapper;

import org.mapstruct.MapperConfig;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@MapperConfig(componentModel = "spring")
public interface BaseMapperConfig {

	@Mapping(target = "id", ignore = true)
	void mapBaseFields(@MappingTarget Object entity, Object dto);
}
