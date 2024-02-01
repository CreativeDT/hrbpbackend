package com.hrbp.feedback.model.dto;

import lombok.*;

@Data
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@AllArgsConstructor
public class DepartmentDTO {
	private Integer departmentId;
	private String departmentName;
}
