package com.hrbp.feedback.model.mapper;

import com.hrbp.feedback.model.dto.DepartmentDto;
import com.hrbp.feedback.model.entity.Department;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", config = BaseMapperConfig.class)
public interface DepartmentMapper {
    DepartmentDto toDto(Department department);
    Department toEntity(DepartmentDto departmentDto);
}
