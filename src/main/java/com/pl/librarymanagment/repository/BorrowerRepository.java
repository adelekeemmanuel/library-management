package com.pl.librarymanagment.repository;

import com.pl.librarymanagment.entity.Borrower;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface BorrowerRepository extends JpaRepository<Borrower, Long>{

    // Check if email already exists
    Optional<Borrower> findByEmail(String email);

    // Check if member ID already exists
    Optional<Borrower> findByMemberId(String memberId);


}
