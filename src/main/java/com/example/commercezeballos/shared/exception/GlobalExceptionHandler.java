package com.example.commercezeballos.shared.exception;


import com.example.commercezeballos.shared.exception.dto.ErrorMessageResponse;
import com.example.commercezeballos.shared.model.dto.response.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    @ResponseStatus(value= HttpStatus.NOT_FOUND)
    public ApiResponse<ErrorMessageResponse> handleResourceNotFoundException(
            ResourceNotFoundException exception,
            WebRequest webRequest
    ){
        var errorMessageResponse= new ErrorMessageResponse(
                LocalDateTime.now(),
                exception.getMessage(),
                webRequest.getDescription(false)
        );
        log.error(exception.getMessage());
        return new ApiResponse<>(false, exception.getMessage(), errorMessageResponse);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(value= HttpStatus.BAD_REQUEST)
    protected ApiResponse<?> handleValidationErrors(
            MethodArgumentNotValidException exception,
            WebRequest webRequest
    ){
        List<String> errors= exception.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(error-> error.getField()+": "+error.getDefaultMessage())
                .collect(Collectors.toList());

        return new ApiResponse<>(false, "Validation errors", errors);
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(value= HttpStatus.INTERNAL_SERVER_ERROR)
    public ApiResponse<ErrorMessageResponse> handleGlobalException(
            Exception exception,
            WebRequest webRequest
    ){
        if (exception instanceof AccessDeniedException) {
            log.error("Access denied exception: {}", exception.getMessage());
            // No manejes explícitamente el error 403 aquí
            throw (AccessDeniedException) exception;
        }
        var errorMessage = new ErrorMessageResponse(
                LocalDateTime.now(),
                exception.getMessage(),
                webRequest.getDescription(false)
        );

        return new ApiResponse<>(false,"Internal server error", errorMessage);
    }

    @ExceptionHandler(ApplicationException.class)
    public ResponseEntity<ApiResponse<ErrorMessageResponse>> handleApplicationException(
            ApplicationException exception,
            WebRequest webRequest
    ){
        var errorMessageResponse= new ErrorMessageResponse(
                LocalDateTime.now(),
                exception.getMessage(),
                webRequest.getDescription(false)
        );
        log.error(exception.getMessage());
        return new ResponseEntity<>(new ApiResponse<>(false, exception.getMessage(), errorMessageResponse), exception.getHttpStatus());
    }



}
