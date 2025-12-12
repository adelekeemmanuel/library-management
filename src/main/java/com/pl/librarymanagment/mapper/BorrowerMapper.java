package com.pl.librarymanagment.mapper;

import com.pl.librarymanagment.dto.*;
import com.pl.librarymanagment.entity.Borrower;

public class BorrowerMapper {

    public static Borrower toEntity(BorrowerRequest request) {
        Borrower b = new Borrower();
        b.setName(request.name());
        b.setEmail(request.email());
        b.setMemberId(request.memberId());
        return b;
    }

    public static void updateEntity(Borrower borrower, BorrowerUpdateRequest request) {
        borrower.setName(request.name());
        borrower.setEmail(request.email());
    }

    public static BorrowerResponse toResponse(Borrower borrower) {
        return new BorrowerResponse(
                borrower.getId(),
                borrower.getName(),
                borrower.getEmail(),
                borrower.getMemberId()
        );
    }
}
