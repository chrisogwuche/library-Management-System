package org.example.bookservice.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BookRequest {
    private String  isbn;
    private String userId;
}
