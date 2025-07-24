package com.rf.spring_oauth2.dto;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Data
public class LoginRequest {
    @NotNull
    private String email;
    @NotNull
    private String password;
}
