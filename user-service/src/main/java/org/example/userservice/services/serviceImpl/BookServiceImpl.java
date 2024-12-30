package org.example.userservice.services.serviceImpl;

import lombok.RequiredArgsConstructor;
import org.example.userservice.dto.AddBookRequest;
import org.example.userservice.entity.Books;
import org.example.userservice.entity.Users;
import org.example.userservice.exceptions.BadRequestException;
import org.example.userservice.repository.BooksRepository;
import org.example.userservice.services.BookService;
import org.example.userservice.utils.Utility;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

    private final BooksRepository booksRepository;
    private final Utility utility;

    @Override
    public Map<String, String> addBook(AddBookRequest request) {
        validateIsbn(request.getIsbn());
        booksRepository.save(Books.from(request));
        return Map.of("message","book added successfully");
    }

    @Override
    public Books borrowBook(String isbn, Users user) {
        Books book = utility.getBookByISBN(isbn);

        if(book.isBorrowed()){
            throw new BadRequestException("Book is already borrowed");
        }
        book.setBorrowed(true);
        return booksRepository.save(book);
    }

    @Override
    public Books returnBook(String isbn) {
        Books book = utility.getBookByISBN(isbn);
        if(!book.isBorrowed()){
            throw new BadRequestException("You can not return book because it is not borrowed");
        }
        book.setBorrowed(false);
        return booksRepository.save(book);
    }

    @Override
    public List<Books> searchBook(String keyword) {
        return booksRepository.fetchByTitleOrAuthor(keyword);
    }

    private void validateIsbn(String isbn){
        if(booksRepository.existsBooksByIsbn(isbn)){
            throw new BadRequestException("Book already exist");
        }
    }
}
