package com.pl.librarymanagment.repository;

import com.pl.librarymanagment.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;
public interface BookRepository extends JpaRepository<Book, Long> {

    // Search by partial title match (case insensitive)
    List<Book> findByTitleContainingIgnoreCase(String title);

    // Search by partial author match (case insensitive)
    List<Book> findByAuthorContainingIgnoreCase(String author);

    // Search by exact ISBN
    Optional<Book> findByIsbn(String isbn);

}
