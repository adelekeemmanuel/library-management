package com.pl.librarymanagment.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record BorrowerRequest(
        @NotBlank(message = "Name is required") String name,
        @Email(message = "Email must be valid") String email,
        @NotBlank(message = "Member ID is required") String memberId
) {}

