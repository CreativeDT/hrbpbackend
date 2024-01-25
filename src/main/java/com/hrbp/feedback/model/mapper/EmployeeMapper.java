package com.hrbp.feedback.model.mapper;

import com.hrbp.feedback.model.dto.EmployeeDto;
import com.hrbp.feedback.model.entity.Employee;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring", config = BaseMapperConfig.class)
public interface EmployeeMapper {

    @Mapping(target = "manager", source = "employee.manager")
    @Mapping(target = "buHead", source = "employee.buHead")
    EmployeeDto toDto(Employee employee);
    Employee toEntity(EmployeeDto employeeDto);

    List<EmployeeDto> toDtoList(List<Employee> employees);
}
