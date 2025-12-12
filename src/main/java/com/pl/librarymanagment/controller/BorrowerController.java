package com.pl.librarymanagment.controller;

import com.pl.librarymanagment.dto.BorrowerRequest;
import com.pl.librarymanagment.dto.BorrowerResponse;
import com.pl.librarymanagment.dto.BorrowerUpdateRequest;
import com.pl.librarymanagment.service.BorrowerService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/borrowers")
public class BorrowerController {

    private final BorrowerService borrowerService;

    public BorrowerController(BorrowerService borrowerService) {
        this.borrowerService = borrowerService;
    }

    // Create
    @PostMapping
    public BorrowerResponse createBorrower(@RequestBody @Valid BorrowerRequest request) {
        return borrowerService.createBorrower(request);
    }

    // Update
    @PutMapping("/{id}")
    public BorrowerResponse updateBorrower(
            @PathVariable Long id,
            @RequestBody @Valid BorrowerUpdateRequest request) {
        return borrowerService.updateBorrower(id, request);
    }

    // Delete
    @DeleteMapping("/{id}")
    public void deleteBorrower(@PathVariable Long id) {
        borrowerService.deleteBorrower(id);
    }

    // Get one
    @GetMapping("/{id}")
    public BorrowerResponse getBorrower(@PathVariable Long id) {
        return borrowerService.getBorrowerById(id);
    }

    // List all
    @GetMapping
    public List<BorrowerResponse> getAllBorrowers() {
        return borrowerService.getAllBorrowers();
    }
}

