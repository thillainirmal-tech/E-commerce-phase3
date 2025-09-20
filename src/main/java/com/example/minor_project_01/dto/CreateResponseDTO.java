package com.example.minor_project_01.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateResponseDTO {
    private Long id;
    private String errorCode;
    private String message;
}
