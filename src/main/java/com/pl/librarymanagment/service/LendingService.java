
package com.pl.librarymanagment.service;

import com.pl.librarymanagment.dto.BorrowRequest;
import com.pl.librarymanagment.dto.LendingRecordResponse;
import com.pl.librarymanagment.dto.ReturnBookRequest;
import com.pl.librarymanagment.entity.Book;
import com.pl.librarymanagment.entity.Borrower;
import com.pl.librarymanagment.entity.LendingRecord;
import com.pl.librarymanagment.exception.AlreadyReturnedException;
import com.pl.librarymanagment.exception.BookUnavailableException;
import com.pl.librarymanagment.exception.ResourceNotFoundException;
import com.pl.librarymanagment.repository.LendingRecordRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class LendingService {

    private final LendingRecordRepository lendingRecordRepository;
    private final BookService bookService;           // to adjust copies and fetch books
    private final BorrowerService borrowerService;   // to fetch borrower entity

    public LendingService(LendingRecordRepository lendingRecordRepository,
                          BookService bookService,
                          BorrowerService borrowerService) {
        this.lendingRecordRepository = lendingRecordRepository;
        this.bookService = bookService;
        this.borrowerService = borrowerService;
    }

    /**
     * Borrow a book: transactional — decrement available copies and create a lending record.
     */
    @Transactional
    public LendingRecordResponse borrowBook(BorrowRequest request) {
        Long bookId = request.bookId();
        Long borrowerId = request.borrowerId();

        // Load entities
        // BookService provides entity-level access via repository; we fetch through repository to adjust copies atomically
        Book book = bookService
                .getBookEntityOrThrow(bookId); // helper method we will add note about below

        Borrower borrower = borrowerService.findEntityById(borrowerId);

        // Check availability
        if (book.getAvailableCopies() <= 0) {
            throw new BookUnavailableException("No copies available for book id: " + bookId);
        }

        // Decrement available copies (in-memory)
        book.setAvailableCopies(book.getAvailableCopies() - 1);
        // Persist updated book (we rely on BookService / repo)
        bookService.saveEntity(book); // helper method; we'll show recommended additions below

        // Create lending record
        LendingRecord record = new LendingRecord();
        record.setBook(book);
        record.setBorrower(borrower);
        record.setBorrowedAt(LocalDate.now());
        // default loan period 14 days (this can be configurable)
        record.setDueDate(LocalDate.now().plusDays(14));
        record.setReturnedAt(null);

        LendingRecord saved = lendingRecordRepository.save(record);
        return toResponse(saved);
    }

    /**
     * Return a book.
     */
    @Transactional
    public LendingRecordResponse returnBook(ReturnBookRequest request) {
        Long recordId = request.lendingRecordId();
        LendingRecord record = lendingRecordRepository.findById(recordId)
                .orElseThrow(() -> new ResourceNotFoundException("Lending record not found with id: " + recordId));

        if (record.getReturnedAt() != null) {
            throw new AlreadyReturnedException("This record has already been returned: " + recordId);
        }

        // mark returned
        record.setReturnedAt(LocalDate.now());
        lendingRecordRepository.save(record);

        // increment available copies for the book
        Book book = record.getBook();
        book.setAvailableCopies(book.getAvailableCopies() + 1);
        bookService.saveEntity(book); // helper method; see note below

        return toResponse(record);
    }

    /**
     * Borrowing history for a borrower
     */
    public List<LendingRecordResponse> getBorrowingHistory(Long borrowerId) {
        // verify borrower exists
        borrowerService.findEntityById(borrowerId);

        return lendingRecordRepository.findByBorrowerIdOrderByBorrowedAtDesc(borrowerId)
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    /**
     * Currently borrowed (not yet returned)
     */
    public List<LendingRecordResponse> getCurrentlyBorrowed() {
        return lendingRecordRepository.findByReturnedAtIsNull()
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    /* --------------------- Helper mapping --------------------- */
    private LendingRecordResponse toResponse(LendingRecord r) {
        return new LendingRecordResponse(
                r.getId(),
                r.getBook().getId(),
                r.getBook().getTitle(),
                r.getBorrower().getId(),
                r.getBorrower().getName(),
                r.getBorrowedAt(),
                r.getDueDate(),
                r.getReturnedAt()
        );
    }
}

