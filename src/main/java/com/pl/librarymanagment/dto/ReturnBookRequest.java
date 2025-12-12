package com.pl.librarymanagment.dto;

import jakarta.validation.constraints.NotNull;

public record ReturnBookRequest(
        @NotNull(message = "Lending Record ID is required") Long lendingRecordId
) {}

