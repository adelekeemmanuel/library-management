package com.pl.librarymanagment.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

public record BookUpdateRequest(
        @NotBlank(message = "Title is required") String title,
        @NotBlank(message = "Author is required") String author,
        @Min(value = 1, message = "Total copies must be at least 1") int totalCopies,
        @Min(value = 0, message = "Available copies cannot be negative") int availableCopies
) {}

