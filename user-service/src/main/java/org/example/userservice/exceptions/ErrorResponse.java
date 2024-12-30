package org.example.userservice.exceptions;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ErrorResponse {
    private Integer code;
    private String message;
    private String url;
}