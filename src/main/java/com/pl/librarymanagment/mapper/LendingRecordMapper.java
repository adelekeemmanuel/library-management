package com.pl.librarymanagment.mapper;

import com.pl.librarymanagment.dto.LendingRecordResponse;
import com.pl.librarymanagment.entity.LendingRecord;

public class LendingRecordMapper {

    public static LendingRecordResponse toResponse(LendingRecord record) {
        return new LendingRecordResponse(
                record.getId(),
                record.getBook().getId(),
                record.getBook().getTitle(),
                record.getBorrower().getId(),
                record.getBorrower().getName(),
                record.getBorrowedAt(),
                record.getDueDate(),
                record.getReturnedAt()
        );
    }
}

