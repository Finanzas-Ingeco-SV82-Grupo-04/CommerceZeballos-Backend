package com.example.commercezeballos.shared.exception;


import lombok.*;
import org.springframework.http.HttpStatus;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ApplicationException extends RuntimeException{
    private HttpStatus httpStatus;

    public ApplicationException(HttpStatus httpStatus, String _message) {
        super(_message);
        this.httpStatus = httpStatus;
    }
}
