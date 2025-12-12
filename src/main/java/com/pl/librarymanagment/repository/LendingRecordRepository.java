package com.pl.librarymanagment.repository;

import com.pl.librarymanagment.entity.LendingRecord;
import com.pl.librarymanagment.entity.Borrower;
import com.pl.librarymanagment.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;


public interface LendingRecordRepository extends JpaRepository<LendingRecord, Long> {

    // Get all records borrowed by one user
    List<LendingRecord> findByBorrowerId(Long borrowerId);

    // Get active borrowed books (returnedAt == null)
    List<LendingRecord> findByReturnedAtIsNull();

    // Get borrowing records of a specific book
    List<LendingRecord> findByBookId(Long bookId);

    List<LendingRecord> findByBorrowerIdOrderByBorrowedAtDesc(Long borrowerId);
//    List<LendingRecord> findByReturnedAtIsNull();


}
