package org.example.bookservice.services;

import org.example.bookservice.dto.BookRequest;
import org.example.bookservice.dto.UserRequest;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public interface UserService {
    Map<String, String> createUser(UserRequest request);
    Map<String, Object> getUserDetails(String userId);
    Map<String,String> borrowBook(BookRequest request);
    Map<String,String> returnBook(BookRequest request);
}
