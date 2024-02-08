package com.hrbp.feedback.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
@Data
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@AllArgsConstructor
public class ActionItemDto {

    private Integer id;
    private String description;
    private String RAG;
    private Integer actionOwner;
    private String status;

}
