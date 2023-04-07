package pl.lukasik.urlShortener.url.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import pl.lukasik.urlShortener.url.model.dto.LongUrlDto;
import pl.lukasik.urlShortener.url.model.dto.ShortUrlDto;
import pl.lukasik.urlShortener.url.service.UrlService;


/**
 * UrlController class handles HTTP requests related to URL shortening and redirection.
 */
@RestController
@RequestMapping("/api/v1/url")
public class UrlController {

    private final UrlService urlService;

    public UrlController(UrlService urlService) {
        this.urlService = urlService;
    }

    /**
     * Shortens the given long URL.
     * @param longUrl LongUrlDto containing the long URL to be shortened.
     * @param allowShorting Optional flag, to shorten the URL even if it is longer than the original one
     * @return ShortUrlDto containing the shortened URL.
     */
    @PostMapping("/shortener")
    public ShortUrlDto shortenUrl(@RequestBody LongUrlDto longUrl,
                                  @RequestParam(required = false, defaultValue = "false") boolean allowShorting) {
        return urlService.shortenUrl(longUrl.getLongUrl(), allowShorting);
    }

    /**
     * Redirects the user to the original URL corresponding to the given short URL ID.
     * @param id Short URL ID.
     * @return ResponseEntity containing the redirection status and headers.
     */
    @GetMapping("/redirect/{id}")
    public ResponseEntity<Void> redirectShortUrl(@PathVariable String id) {
        String retrieveUrl = urlService.getOriginalUrl(id);
        return ResponseEntity.status(HttpStatus.MOVED_PERMANENTLY)
                .header("Location", retrieveUrl)
                .build();
    }



}
