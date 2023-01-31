package pl.lukasik.urlShortener.url.model.dto;


import lombok.*;

import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

@Builder
@Getter
@AllArgsConstructor
public class ShortUrlDto {

     public ShortUrlDto(String short_url) {
        this.short_url = short_url;
    }

    private String short_url;
    private String original_url;
    private Instant created_at;



}
