package com.hrbp.feedback.model.dto;

import lombok.*;

import java.time.LocalDateTime;

@Data
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@AllArgsConstructor
public class FeedbackDTO {
	private Integer ticketId;
	private Integer creatorId;
	private Integer assignedManagerId;
	private String concerns;
	private String remarks;
	private String expert;
	private String status;
	private LocalDateTime dateCreated;
	private LocalDateTime lastStatusChangeDate;
	private Integer employeeId;
}
