package com.hrbp.feedback.model.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Entity
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@AllArgsConstructor
@Table(name = "feedback_master_table")
public class Feedback {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ticket_id")
	private Integer ticketId;

	// Employee ID of the person providing feedback
	@Column(name = "creator_id")
	private Integer creatorId;

	// Manager responsible for addressing the feedback
	@Column(name = "assigned_manager_id")
	private Integer assignedManagerId;

	@Column(name = "concerns")
	private String concerns;

	@Column(name = "remarks")
	private String remarks;

	@Column(name = "expert")
	private String expert;

	@Column(name = "status")
	private String status;

//	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm")
	@Column(name = "created_on")
	private LocalDateTime dateCreated;

//	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm")
	@Column(name = "updated_on")
	private LocalDateTime lastStatusChangeDate;
	
	@Column(name="employee_id")
	private Integer employeeId;

}
