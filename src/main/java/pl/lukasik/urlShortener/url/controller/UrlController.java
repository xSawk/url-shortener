package pl.lukasik.urlShortener.url.controller;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.lukasik.urlShortener.url.model.dto.ShortUrlDto;
import pl.lukasik.urlShortener.url.service.UrlService;

@RestController
public class UrlController {

    private final UrlService urlService;

    public UrlController(UrlService urlService) {
        this.urlService = urlService;
    }

    @PostMapping("/api/shortener/")
    public ShortUrlDto shortenUrl(@RequestBody String longUrl) {
        return urlService.shortenUrl(longUrl);
    }

    @GetMapping("/{shortUrl}")
    public ResponseEntity<Void> redirectShortUrl(@PathVariable String shortUrl) {
        String retrieveUrl = urlService.getOriginalUrl(shortUrl);
        if (!retrieveUrl.startsWith("https://")) {
            retrieveUrl = "https://" + retrieveUrl;
        }
        return ResponseEntity.status(HttpStatus.MOVED_PERMANENTLY)
                .header("Location", retrieveUrl)
                .build();
    }
}