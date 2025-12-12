
package com.pl.librarymanagment.mapper;

import com.pl.librarymanagment.dto.BookRequest;
import com.pl.librarymanagment.dto.BookResponse;
import com.pl.librarymanagment.dto.BookUpdateRequest;
import com.pl.librarymanagment.entity.Book;

public class BookMapper {

    public static Book toEntity(BookRequest request) {
        Book book = new Book();
        book.setTitle(request.title());
        book.setAuthor(request.author());
        book.setIsbn(request.isbn());
        book.setTotalCopies(request.totalCopies());
        book.setAvailableCopies(request.totalCopies()); // new books start with full availability
        return book;
    }

    public static void updateEntity(Book book, BookUpdateRequest request) {
        book.setTitle(request.title());
        book.setAuthor(request.author());
        book.setTotalCopies(request.totalCopies());
        book.setAvailableCopies(request.availableCopies());
    }

    public static BookResponse toResponse(Book book) {
        return new BookResponse(
                book.getId(),
                book.getTitle(),
                book.getAuthor(),
                book.getIsbn(),
                book.getTotalCopies(),
                book.getAvailableCopies()
        );
    }
}

