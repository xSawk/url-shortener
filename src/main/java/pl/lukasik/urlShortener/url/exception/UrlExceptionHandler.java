package pl.lukasik.urlShortener.url.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.Instant;
import java.time.ZonedDateTime;


@ControllerAdvice
public class UrlExceptionHandler {

    @ExceptionHandler(value = {InvalidUrlException.class})
    public ResponseEntity<Object> handleUrlException(InvalidUrlException e){
        UrlException urlException = new UrlException(
                e.getMessage(),
                HttpStatus.BAD_REQUEST,
                Instant.now()
        );
        return new ResponseEntity<>(urlException, HttpStatus.BAD_REQUEST);
    }
}
