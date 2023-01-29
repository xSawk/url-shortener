package pl.lukasik.urlShortener.url.model.dto;


import lombok.Builder;
import lombok.Getter;

import java.util.Date;

@Builder
@Getter
public class ShortUrlDto {
    private String short_url;
    private Date created_at;
}
