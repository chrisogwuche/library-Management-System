package org.example.bookservice.services;

import org.example.bookservice.dto.AddBookRequest;
import org.example.bookservice.entity.Books;
import org.example.bookservice.entity.Users;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public interface BookService {
    Map<String,String> addBook(AddBookRequest request);
    Books borrowBook(String isbn, Users user);
    Books returnBook(String isbn);
    Page<Books> searchBookForBook(String keyword, int pageSize, int pageNo);
}
