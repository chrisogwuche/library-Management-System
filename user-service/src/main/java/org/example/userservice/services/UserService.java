package org.example.userservice.services;

import org.example.userservice.dto.BookRequest;
import org.example.userservice.dto.UserRequest;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public interface UserService {
    Map<String, String> createUser(UserRequest request);
    Map<String, Object> getUserDetails(String userId);
    Map<String,String> borrowBook(BookRequest request);
    Map<String,String> returnBook(BookRequest request);
}
