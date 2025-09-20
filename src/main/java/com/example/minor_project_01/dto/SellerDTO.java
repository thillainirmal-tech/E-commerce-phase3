package com.example.minor_project_01.dto;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;


@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SellerDTO {


    @NotNull
    @Size(min = 3)
    private String name;
    @NotNull
    @Email
    private String email;
    @NotNull
    private Long companyId;
    @NotNull
    private String password;
}
