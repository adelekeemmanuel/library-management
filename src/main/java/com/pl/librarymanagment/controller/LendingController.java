package com.pl.librarymanagment.controller;

import com.pl.librarymanagment.dto.BorrowRequest;
import com.pl.librarymanagment.dto.LendingRecordResponse;
import com.pl.librarymanagment.dto.ReturnBookRequest;
import com.pl.librarymanagment.service.LendingService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/lending")
public class LendingController {

    private final LendingService lendingService;

    public LendingController(LendingService lendingService) {
        this.lendingService = lendingService;
    }

    // Borrow a book
    @PostMapping("/borrow")
    public LendingRecordResponse borrowBook(@RequestBody @Valid BorrowRequest request) {
        return lendingService.borrowBook(request);
    }

    // Return a book
    @PostMapping("/return")
    public LendingRecordResponse returnBook(@RequestBody @Valid ReturnBookRequest request) {
        return lendingService.returnBook(request);
    }

    // Borrower's history
    @GetMapping("/history/{borrowerId}")
    public List<LendingRecordResponse> borrowerHistory(@PathVariable Long borrowerId) {
        return lendingService.getBorrowingHistory(borrowerId);
    }

    // All currently borrowed (not returned)
    @GetMapping("/current")
    public List<LendingRecordResponse> getCurrentlyBorrowed() {
        return lendingService.getCurrentlyBorrowed();
    }
}

