package com.pl.librarymanagment.dto;

import jakarta.validation.constraints.NotNull;

public record BorrowRequest(
        @NotNull(message = "Book ID is required") Long bookId,
        @NotNull(message = "Borrower ID is required") Long borrowerId
) {}
