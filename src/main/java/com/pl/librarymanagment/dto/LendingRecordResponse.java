package com.pl.librarymanagment.dto;

import java.time.LocalDate;

public record LendingRecordResponse(
        Long id,
        Long bookId,
        String bookTitle,
        Long borrowerId,
        String borrowerName,
        LocalDate borrowedAt,
        LocalDate dueDate,
        LocalDate returnedAt
) {}

