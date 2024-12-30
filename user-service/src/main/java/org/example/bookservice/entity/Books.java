package org.example.bookservice.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.example.bookservice.dto.AddBookRequest;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "books")
@Getter
@Setter
public class Books {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    private String title;
    private String author;
    @Column(unique = true)
    private String isbn;
    private boolean isBorrowed;
    @CreationTimestamp
    private LocalDateTime createdAt;

    public static Books from(AddBookRequest request){
        Books book = new Books();
        book.setAuthor(request.getAuthor());
        book.setIsbn(request.getIsbn());
        book.setTitle(request.getTitle());
        return book;
    }
}
