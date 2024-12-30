package org.example.bookservice.services.serviceImpl;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.bookservice.dto.AddBookRequest;
import org.example.bookservice.dto.BookRequest;
import org.example.bookservice.dto.UserRequest;
import org.example.bookservice.entity.Books;
import org.example.bookservice.entity.Users;
import org.example.bookservice.exceptions.BadRequestException;
import org.example.bookservice.repository.UsersRepository;
import org.example.bookservice.services.BookService;
import org.example.bookservice.services.UserService;
import org.example.bookservice.utils.Utility;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;
import java.util.Objects;

@Component
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {

    private final UsersRepository usersRepository;
    private final BookService bookService;
    private final Utility utility;

    @Override
    public Map<String, String> createUser(UserRequest request) {
        Users savedUser = usersRepository.save(Users.from(request));
        return Map.of("message","user creation successful"
                ,"user_id", savedUser.getUserId());
    }

    @Override
    public Map<String, Object> getUserDetails(String userId) {
        Users user = utility.getUser(userId);
        return Map.of("name", user.getName()
                ,"user_id",user.getUserId()
                ,"borrowed_books",user.getBorrowedBooks());
    }

    @Transactional
    @Override
    public Map<String, String> borrowBook(BookRequest request) {
        Users user = utility.getUser(request.getUserId());

        Books book = bookService.borrowBook(request.getIsbn(),user);

        user.getBorrowedBooks()
                .add(book);
        usersRepository.save(user);

        return Map.of("message","Book borrowed successful");
    }

    @Transactional
    @Override
    public Map<String, String> returnBook(BookRequest request) {

        Users user = utility.getUser(request.getUserId());
        validateBorrowedBook(request.getIsbn(), user);

        Books book = bookService.returnBook(request.getIsbn());

        removeFromBorrowedBook(user, book);
        usersRepository.save(user);

        return Map.of("message","Book returned successful");
    }

    private static void removeFromBorrowedBook(Users user, Books book) {
        user.getBorrowedBooks()
                .stream().anyMatch(b-> {
                    if(Objects.equals(b.getIsbn(), book.getIsbn())){
                        user.getBorrowedBooks().remove(b);
                        return true;
                    }
                    return false;
                });
    }

    private void validateBorrowedBook(String isbn, Users user){
        boolean exist = user.getBorrowedBooks().stream()
                .anyMatch(b-> Objects.equals(b.getIsbn(), isbn));

        if(!exist){
            throw new BadRequestException("Book returned failed. Book not borrowed by user");
        }
    }

    @PostConstruct
    public void onStart(){
//
//        addBook();
//        createUsers();


//        returnBook();
//        borrowBook1();
//        borrowBook2();
    }


    public void createUsers(){
        UserRequest r1 = new UserRequest();
        r1.setName("Chris");
        UserRequest r2 = new UserRequest();
        r2.setName("Mike");

        log.info("user 1: {}", createUser(r1));
        log.info("user 2: {}", createUser(r2));
    }

    public void addBook(){
        AddBookRequest r1 = new AddBookRequest();
        AddBookRequest r2 = new AddBookRequest();

        r1.setAuthor("David");
        r1.setIsbn("8373928393");
        r1.setTitle("I am a man");

        r2.setAuthor("Ochanya");
        r2.setIsbn("8373928394");
        r2.setTitle("I am a Son");

        log.info("Add book 1: {}", bookService.addBook(r1));
        log.info("Add book 2: {}",bookService.addBook(r2));
    }

    public void borrowBook1(){
        BookRequest bookRequest = new BookRequest();
        bookRequest.setIsbn("8373928394");
        bookRequest.setUserId("56becc1a-0556-4e34-b120-db0460bbada7");

        log.info("Borrow book 1 : {}", borrowBook(bookRequest));
    }

    public void borrowBook2(){
        BookRequest bookRequest = new BookRequest();
        bookRequest.setIsbn("8373928393");
        bookRequest.setUserId("56becc1a-0556-4e34-b120-db0460bbada7");

        log.info("Borrow book 2 : {}", borrowBook(bookRequest));
    }

    public void returnBook(){
        BookRequest bookRequest = new BookRequest();
        bookRequest.setIsbn("8373928393");
        bookRequest.setUserId("56becc1a-0556-4e34-b120-db0460bbada7");

        log.info("Return book : {}", returnBook(bookRequest));

    }

}
