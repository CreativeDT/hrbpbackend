package com.hrbp.feedback.model.dto;

import lombok.*;

@Data
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@AllArgsConstructor
public class RoleDto {
    private  Integer roleId;
    private String roleName;
}
