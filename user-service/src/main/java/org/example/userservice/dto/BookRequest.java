package org.example.userservice.dto;

import lombok.Getter;
import lombok.Setter;
import org.example.userservice.validators.MaxBorrowLimit;

@Getter
@Setter
public class BookRequest {
    private String  isbn;
    @MaxBorrowLimit
    private String userId;
}
