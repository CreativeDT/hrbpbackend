package com.hrbp.feedback.model.entity;

import jakarta.persistence.*;
import lombok.*;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Component;

@Entity
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@AllArgsConstructor
@Component
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


	@Column(name = "created_on")
	private String createdDate;


	@Column(name = "updated_on")
	private String updatedDate;

	@Column(name = "employee_id")
	private Integer employeeId;

	@OneToMany(mappedBy = "feedback", cascade = CascadeType.ALL)
	private List<ActionItem> actionItems;

}
