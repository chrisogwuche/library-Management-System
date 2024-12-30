package org.example.libraryservice.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddBookRequest {
    private String title;
    private String author;
    private String isbn;
}
