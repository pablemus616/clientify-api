package com.nihil.clientifyapi.auth.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record LoginRequest(
        @Email
        @Size(max = 50, min = 5)
        String email,
        @NotBlank
        @Size(min = 8, max = 21)
        String password
) {}
