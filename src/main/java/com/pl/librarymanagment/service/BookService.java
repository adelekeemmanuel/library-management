package com.pl.librarymanagment.service;

import com.pl.librarymanagment.dto.BookRequest;
import com.pl.librarymanagment.dto.BookResponse;
import com.pl.librarymanagment.dto.BookUpdateRequest;
import com.pl.librarymanagment.entity.Book;
import com.pl.librarymanagment.exception.ResourceNotFoundException;
import com.pl.librarymanagment.repository.BookRepository;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BookService {

    private final BookRepository bookRepository;

    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    // Create
    public BookResponse createBook(BookRequest request) {
        // optionally check ISBN uniqueness
        bookRepository.findByIsbn(request.isbn()).ifPresent(b ->
        { throw new IllegalArgumentException("ISBN already exists: " + request.isbn()); });

        Book book = new Book();
        book.setTitle(request.title());
        book.setAuthor(request.author());
        book.setIsbn(request.isbn());
        book.setTotalCopies(request.totalCopies());
        // when creating new book, available copies = totalCopies
        book.setAvailableCopies(request.totalCopies());

        Book saved = bookRepository.save(book);
        return toResponse(saved);
    }




    // Update
    public BookResponse updateBook(Long id, BookUpdateRequest request) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Book not found with id: " + id));

        book.setTitle(request.title());
        book.setAuthor(request.author());
        book.setTotalCopies(request.totalCopies());
        // allow updating availableCopies (careful with business rules)
        book.setAvailableCopies(request.availableCopies());

        Book saved = bookRepository.save(book);
        return toResponse(saved);
    }

    // Partial update example (you can implement separately if desired)
    public BookResponse patchUpdate(Long id, BookUpdateRequest request) {
        return updateBook(id, request);
    }

    // Delete
    public void deleteBook(Long id) {
        if (!bookRepository.existsById(id)) {
            throw new ResourceNotFoundException("Book not found with id: " + id);
        }
        bookRepository.deleteById(id);
    }

    // Get single
    public BookResponse getBookById(Long id) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Book not found with id: " + id));
        return toResponse(book);
    }

    // List all (no pagination)
    public List<BookResponse> getAllBooks() {
        return bookRepository.findAll()
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    // Search with simple repository methods (title/author/isbn)
    public List<BookResponse> searchByTitle(String title) {
        return bookRepository.findByTitleContainingIgnoreCase(title)
                .stream().map(this::toResponse).collect(Collectors.toList());
    }

    public List<BookResponse> searchByAuthor(String author) {
        return bookRepository.findByAuthorContainingIgnoreCase(author)
                .stream().map(this::toResponse).collect(Collectors.toList());
    }

    public BookResponse searchByIsbn(String isbn) {
        return bookRepository.findByIsbn(isbn)
                .map(this::toResponse)
                .orElseThrow(() -> new ResourceNotFoundException("Book not found with ISBN: " + isbn));
    }

    // Pageable search (optional)
    public Page<BookResponse> findAll(Pageable pageable) {
        return bookRepository.findAll(pageable).map(this::toResponse);
    }

    /* --------- Methods used by LendingService to adjust copies --------- */

    /**
     * Decrement available copies by 1. Throws IllegalStateException or BookUnavailableException if none available.
     */
    @Transactional
    public void decrementAvailableCopies(Long bookId) {
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new ResourceNotFoundException("Book not found with id: " + bookId));

        if (book.getAvailableCopies() <= 0) {
            throw new IllegalStateException("No available copies for book id: " + bookId);
        }

        book.setAvailableCopies(book.getAvailableCopies() - 1);
        bookRepository.save(book);
    }

    /**
     * Increment available copies by 1.
     */
    @Transactional
    public void incrementAvailableCopies(Long bookId) {
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new ResourceNotFoundException("Book not found with id: " + bookId));

        book.setAvailableCopies(book.getAvailableCopies() + 1);
        bookRepository.save(book);
    }

    /* ----------------- Helper mapper ----------------- */
    private BookResponse toResponse(Book book) {
        return new BookResponse(
                book.getId(),
                book.getTitle(),
                book.getAuthor(),
                book.getIsbn(),
                book.getTotalCopies(),
                book.getAvailableCopies()
        );
    }



//    Keeping this (critical)


    // inside BookService
    public Book getBookEntityOrThrow(Long id) {
        return bookRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Book not found with id: " + id));
    }

    @Transactional
    public Book saveEntity(Book book) {
        return bookRepository.save(book);
    }

}

