package org.example.userservice.validators;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;
import org.example.userservice.repository.BooksRepository;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ISBNValidator implements ConstraintValidator<ValidateISBN,String> {

    private final BooksRepository booksRepository;
    @Override
    public boolean isValid(String isbn, ConstraintValidatorContext context) {
        if (isbn == null || isbn.isEmpty()) {
            return false;
        }
        return !booksRepository.existsBooksByIsbn(isbn);
    }
}
