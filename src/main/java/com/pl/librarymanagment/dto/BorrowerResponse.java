package com.pl.librarymanagment.dto;

public record BorrowerResponse(
        Long id,
        String name,
        String email,
        String memberId
) {}
