package com.hrbp.feedback.model.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

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

    @Column(name = "creator_id")
    private Integer creatorId; // Employee ID of the person providing feedback

    @Column(name = "assigned_manager_id")
    private Integer assignedManagerId; // Manager responsible for addressing the feedback

    @Column(name = "ticket_details")
    private String ticketDetails;

    @Column(name = "status")
    private String status; // e.g., "Open", "In Progress", "Resolved"

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(name = "created_on")
    private LocalDateTime dateCreated;


    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(name = "updated_on")
    private LocalDateTime lastStatusChangeDate;


}

