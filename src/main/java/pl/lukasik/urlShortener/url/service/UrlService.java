package pl.lukasik.urlShortener.url.service;


import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import pl.lukasik.urlShortener.url.model.Url;
import pl.lukasik.urlShortener.url.model.dto.ShortUrlDto;
import pl.lukasik.urlShortener.url.repository.UrlRepository;

import java.util.Date;
import java.util.Optional;


@Service

public class UrlService {

    private final UrlRepository urlRepository;
    @Value("${shortenerHost}")
    private String shortenerHost;

    public UrlService(UrlRepository urlRepository) {
        this.urlRepository = urlRepository;
    }


    public ShortUrlDto shortenUrl(String longUrl) {
        Url url = saveUrl(longUrl);
        return ShortUrlDto.builder()
                  .short_url(url.getShort_url())
                  .created_at(url.getCreated_at())
                  .build();
    }

    public String getOriginalUrl(String shortUrl) {
        String[] parts = shortUrl.split("/");
        String objectId = parts[parts.length - 1];
        Url retrieveUrl = urlRepository.findById(objectId).orElseThrow();
        return retrieveUrl.getOriginal_url();
    }

    private Url saveUrl(String longUrl) {
        ObjectId newObjectId = new ObjectId();
        Url url = new Url();
        url.set_id(newObjectId);
        url.setOriginal_url(longUrl);
        url.setShort_url(shortenerHost+newObjectId);
        url.setCreated_at(new Date());
        urlRepository.save(url);
        return url;
    }


}
