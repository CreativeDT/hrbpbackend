package com.hrbp.feedback.model.dto;

import lombok.*;

@Data
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@AllArgsConstructor
public class DepartmentDto {

    private  Integer departmentId;
    private String departmentName;
}
