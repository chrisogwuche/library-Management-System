package org.example.userservice.services.serviceImpl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.userservice.dto.BookRequest;
import org.example.userservice.dto.UserRequest;
import org.example.userservice.entity.Books;
import org.example.userservice.entity.Users;
import org.example.userservice.exceptions.BadRequestException;
import org.example.userservice.repository.UsersRepository;
import org.example.userservice.services.BookService;
import org.example.userservice.services.UserService;
import org.example.userservice.utils.Utility;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
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
        log.info("borrowBook::");
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
        log.info("returnBook::");

        Users user = utility.getUser(request.getUserId());
        validateBorrowedBook(request.getIsbn(), user);

        Books book = bookService.returnBook(request.getIsbn());

        removeFromBorrowedBook(user, book);
        usersRepository.save(user);

        return Map.of("message","Book returned successful");
    }

    @Override
    public List<Users> getAllUsers() {
        Sort sort = Sort.by(Sort.Order.desc("createdAt"));
        return usersRepository.findAll(sort);
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
}
