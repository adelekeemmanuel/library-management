package com.pl.librarymanagment.dto;

public record BookResponse(
        Long id,
        String title,
        String author,
        String isbn,
        Integer totalCopies,
        Integer availableCopies
) {}

