package com.hrbp.feedback.model.dto;

import lombok.*;

import java.time.LocalDateTime;

@Data
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@AllArgsConstructor
public class FeedbackDto {

    private Integer ticketId;
    private Integer creatorId;
    private Integer assignedManagerId;
    private String ticketDetails;
    private String status;
    private LocalDateTime dateCreated;
    private LocalDateTime lastStatusChangeDate;


}
