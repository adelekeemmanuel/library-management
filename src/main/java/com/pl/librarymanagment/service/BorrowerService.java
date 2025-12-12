package com.pl.librarymanagment.service;

import com.pl.librarymanagment.dto.BorrowerRequest;
import com.pl.librarymanagment.dto.BorrowerResponse;
import com.pl.librarymanagment.dto.BorrowerUpdateRequest;
import com.pl.librarymanagment.entity.Borrower;
import com.pl.librarymanagment.exception.ResourceNotFoundException;
import com.pl.librarymanagment.repository.BorrowerRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BorrowerService {

    private final BorrowerRepository borrowerRepository;

    public BorrowerService(BorrowerRepository borrowerRepository) {
        this.borrowerRepository = borrowerRepository;
    }

    // Create borrower
    public BorrowerResponse createBorrower(BorrowerRequest request) {
        // You may want to check unique email or memberId here
        Borrower b = new Borrower();
        b.setName(request.name());
        b.setEmail(request.email());
        b.setMemberId(request.memberId());
        Borrower saved = borrowerRepository.save(b);
        return toResponse(saved);
    }

    // Update borrower
    public BorrowerResponse updateBorrower(Long id, BorrowerUpdateRequest request) {
        Borrower b = borrowerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Borrower not found with id: " + id));

        b.setName(request.name());
        b.setEmail(request.email());
        Borrower saved = borrowerRepository.save(b);
        return toResponse(saved);
    }

    // Delete
    public void deleteBorrower(Long id) {
        if (!borrowerRepository.existsById(id)) {
            throw new ResourceNotFoundException("Borrower not found with id: " + id);
        }
        borrowerRepository.deleteById(id);
    }

    // Get by id
    public BorrowerResponse getBorrowerById(Long id) {
        Borrower b = borrowerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Borrower not found with id: " + id));
        return toResponse(b);
    }

    // List all
    public List<BorrowerResponse> getAllBorrowers() {
        return borrowerRepository.findAll().stream().map(this::toResponse).collect(Collectors.toList());
    }

    // Internal use (returns entity) — useful for LendingService
    public Borrower findEntityById(Long id) {
        return borrowerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Borrower not found with id: " + id));
    }

    private BorrowerResponse toResponse(Borrower b) {
        return new BorrowerResponse(b.getId(), b.getName(), b.getEmail(), b.getMemberId());
    }
}
