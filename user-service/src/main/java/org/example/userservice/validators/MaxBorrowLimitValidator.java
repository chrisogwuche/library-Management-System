package org.example.userservice.validators;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.userservice.entity.Users;
import org.example.userservice.repository.UsersRepository;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
@RequiredArgsConstructor
@Slf4j
public class MaxBorrowLimitValidator implements ConstraintValidator<MaxBorrowLimit,String> {
    private final UsersRepository usersRepository;

    @Override
    public boolean isValid(String userId, ConstraintValidatorContext context) {
        if (userId == null || userId.isEmpty()) {
            return false;
        }
        Users user = usersRepository.findByUserId(userId).orElse(null);
        if(Objects.nonNull(user)){
           return user.getBorrowedBooks().size() <= 4;
        }
        return false;
    }
}
