package pl.lukasik.urlShortener.url.controller;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.lukasik.urlShortener.url.model.dto.LongUrlDto;
import pl.lukasik.urlShortener.url.model.dto.ShortUrlDto;
import pl.lukasik.urlShortener.url.service.UrlService;



@RestController
public class UrlController {

    private final UrlService urlService;

    public UrlController(UrlService urlService) {
        this.urlService = urlService;
    }

    @PostMapping("/api/shortener")
    public ShortUrlDto shortenUrl(@RequestBody LongUrlDto longUrl,
                                  @RequestParam(required = false, defaultValue = "false") boolean flag) {
        return urlService.shortenUrl(longUrl.getLongUrl(), flag);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Void> redirectShortUrl(@PathVariable String id) {
        String retrieveUrl = urlService.getOriginalUrl(id);
        return ResponseEntity.status(HttpStatus.MOVED_PERMANENTLY)
                .header("Location", retrieveUrl)
                .build();
    }
}
