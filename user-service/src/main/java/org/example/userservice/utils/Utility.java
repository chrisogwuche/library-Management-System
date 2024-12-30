package org.example.userservice.utils;

import lombok.RequiredArgsConstructor;
import org.example.userservice.entity.Books;
import org.example.userservice.entity.Users;
import org.example.userservice.exceptions.NotFoundException;
import org.example.userservice.repository.BooksRepository;
import org.example.userservice.repository.UsersRepository;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class Utility {

    private final UsersRepository usersRepository;
    private final BooksRepository booksRepository;

    public Books getBookByISBN(String isbn){
        return booksRepository.findByIsbn(isbn)
                .orElseThrow(()-> new NotFoundException("Book not found") );
    }

    public Users getUser(String userId){
        return usersRepository.findByUserId(userId)
                .orElseThrow(()-> new NotFoundException("User not found"));
    }
}
