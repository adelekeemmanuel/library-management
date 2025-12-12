package com.pl.librarymanagment.controller;

import com.pl.librarymanagment.dto.BookRequest;
import com.pl.librarymanagment.dto.BookResponse;
import com.pl.librarymanagment.dto.BookUpdateRequest;
import com.pl.librarymanagment.service.BookService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/books")
public class BookController {

    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    // Create
    @PostMapping
    public BookResponse createBook(@RequestBody @Valid BookRequest request) {
        return bookService.createBook(request);
    }

    // Update
    @PutMapping("/{id}")
    public BookResponse updateBook(
            @PathVariable Long id,
            @RequestBody @Valid BookUpdateRequest request) {
        return bookService.updateBook(id, request);
    }

    // Delete
    @DeleteMapping("/{id}")
    public void deleteBook(@PathVariable Long id) {
        bookService.deleteBook(id);
    }

    // Get one
    @GetMapping("/{id}")
    public BookResponse getBook(@PathVariable Long id) {
        return bookService.getBookById(id);
    }

    // List all
    @GetMapping
    public List<BookResponse> getAllBooks() {
        return bookService.getAllBooks();
    }

    // Search by title
    @GetMapping("/search/title")
    public List<BookResponse> searchByTitle(@RequestParam String title) {
        return bookService.searchByTitle(title);
    }

    // Search by author
    @GetMapping("/search/author")
    public List<BookResponse> searchByAuthor(@RequestParam String author) {
        return bookService.searchByAuthor(author);
    }

    // Search by ISBN
    @GetMapping("/search/isbn")
    public BookResponse searchByIsbn(@RequestParam String isbn) {
        return bookService.searchByIsbn(isbn);
    }
}

