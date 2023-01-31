package pl.lukasik.urlShortener.url.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.time.Instant;
import java.time.ZonedDateTime;


@Getter
public class UrlException {
    private final String message;
    private final HttpStatus httpStatus;
    private final Instant timestamp;


    public UrlException(String message, HttpStatus httpStatus, Instant timestamp) {
        this.message = message;
        this.httpStatus = httpStatus;
        this.timestamp = timestamp;
    }
}
