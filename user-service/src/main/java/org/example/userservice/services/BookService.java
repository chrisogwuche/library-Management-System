package org.example.userservice.services;

import org.example.userservice.dto.AddBookRequest;
import org.example.userservice.entity.Books;
import org.example.userservice.entity.Users;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public interface BookService {
    Map<String,String> addBook(AddBookRequest request);
    Books borrowBook(String isbn, Users user);
    Books returnBook(String isbn);
    Page<Books> searchBook(String keyword, int pageSize, int pageNo);
}
