package pl.lukasik.urlShortener.url.model.dto;


import lombok.Builder;
import lombok.Getter;

import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

@Builder
@Getter
public class ShortUrlDto {
    private String short_url;
    private String original_url;
    private Instant created_at;



}
