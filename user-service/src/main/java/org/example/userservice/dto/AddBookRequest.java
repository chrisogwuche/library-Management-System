package org.example.userservice.dto;

import lombok.Getter;
import lombok.Setter;
import org.example.userservice.validators.ValidateISBN;

@Getter
@Setter
public class AddBookRequest {
    private String title;
    private String author;
    @ValidateISBN
    private String isbn;
}
