package org.example.userservice.controller;


import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.userservice.dto.AddBookRequest;
import org.example.userservice.dto.BookRequest;
import org.example.userservice.dto.UserDto;
import org.example.userservice.entity.Books;
import org.example.userservice.entity.Users;
import org.example.userservice.services.BookService;
import org.example.userservice.services.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final BookService bookService;
    private final UserService userService;

    @PostMapping("/add-book")
    public ResponseEntity<Map<String,String>> addBook(@Valid @RequestBody AddBookRequest request){
        return ResponseEntity.ok(bookService.addBook(request));
    }

    @PostMapping("/borrow-book")
    public ResponseEntity<Map<String,String>> borrowBook(@Valid @RequestBody BookRequest request){
        return ResponseEntity.ok(userService.borrowBook(request));
    }

    @PostMapping("/return-book")
    public ResponseEntity<Map<String,String>> returnBook(@RequestBody BookRequest request){
        return ResponseEntity.ok(userService.returnBook(request));
    }

    @PostMapping("/user-details")
    public ResponseEntity<Map<String,Object>> userDetails(@RequestBody UserDto request){
        return ResponseEntity.ok(userService.getUserDetails(request.getId()));
    }

    @PostMapping("/search")
    public ResponseEntity<List<Books>> searchBook(@RequestBody UserDto request){
        return ResponseEntity.ok(bookService.searchBook(request.getKeyword()));
    }

    @GetMapping("/all-users")
    public ResponseEntity<List<Users>> allUser(){
        return ResponseEntity.ok(userService.getAllUsers());
    }
}
