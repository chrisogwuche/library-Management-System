package org.example.userservice.services;

import org.example.userservice.dto.BookRequest;
import org.example.userservice.dto.UserRequest;
import org.example.userservice.entity.Users;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public interface UserService {
    Map<String, String> createUser(UserRequest request);
    Map<String, Object> getUserDetails(String userId);
    Map<String,String> borrowBook(BookRequest request);
    Map<String,String> returnBook(BookRequest request);
    List<Users> getAllUsers();
}
