package org.example.userservice.repository;

import org.example.userservice.entity.Books;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BooksRepository extends JpaRepository<Books, Long> {

    @Query("SELECT b FROM Books b WHERE (LOWER(b.title) LIKE LOWER(CONCAT('%', ?1, '%')) " +
            "OR LOWER(b.author) LIKE LOWER(CONCAT('%', ?1, '%'))) " +
            "ORDER BY b.createdAt DESC")
    List<Books> fetchByTitleOrAuthor(String search);

    boolean existsBooksByIsbn(String isbn);

    Optional<Books> findByIsbn(String isbn);
}