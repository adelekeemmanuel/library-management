package com.pl.librarymanagment.entity;

//import jakarta.persistence.Column;
//import jakarta.persistence.Entity;
//import jakarta.persistence.Id;
//import jakarta.persistence.Table;
//import lombok.Data;
//import lombok.NoArgsConstructor;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@Table(name = "lending_records")
@NoArgsConstructor
public class LendingRecord {

    @jakarta.persistence.Id
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Book book;

    @ManyToOne
    private Borrower borrower;

    private LocalDate borrowedAt;
    private LocalDate dueDate;
    private LocalDate returnedAt;


}
