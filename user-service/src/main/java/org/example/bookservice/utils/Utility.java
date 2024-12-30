package org.example.bookservice.utils;

import lombok.RequiredArgsConstructor;
import org.example.bookservice.entity.Books;
import org.example.bookservice.entity.Users;
import org.example.bookservice.exceptions.NotFoundException;
import org.example.bookservice.repository.BooksRepository;
import org.example.bookservice.repository.UsersRepository;
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
