package com.hrbp.feedback.model.dto;


import com.hrbp.feedback.model.entity.Employee;
import com.hrbp.feedback.model.entity.Feedback;
import lombok.*;

import java.util.Date;
import java.util.List;

@Data
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@AllArgsConstructor
public class EmployeeDto {

    private Integer employeeId;
    private String firstName;
    private String lastName;
    private RoleDto role;
    private DepartmentDto department;
    private Employee manager; // Full Employee object
    private Employee buHead; // Full Employee object
    private Date hireDate;

    private List<Feedback> feedbacks;


}
