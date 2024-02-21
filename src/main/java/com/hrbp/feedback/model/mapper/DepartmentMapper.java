package com.hrbp.feedback.model.mapper;

import com.hrbp.feedback.model.dto.DepartmentDTO;
import com.hrbp.feedback.model.entity.Department;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", config = BaseMapperConfig.class)
public interface DepartmentMapper {
	
	DepartmentDTO toDto(Department department);
	Department toEntity(DepartmentDTO departmentDto);
}
