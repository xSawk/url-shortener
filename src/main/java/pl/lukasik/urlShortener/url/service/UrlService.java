package pl.lukasik.urlShortener.url.service;


import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import pl.lukasik.urlShortener.url.common.UrlUtil;
import pl.lukasik.urlShortener.url.exception.InvalidUrlException;
import pl.lukasik.urlShortener.url.model.Url;
import pl.lukasik.urlShortener.url.model.dto.ShortUrlDto;
import pl.lukasik.urlShortener.url.repository.UrlRepository;

import java.time.Instant;


@Service
public class UrlService {

    private final UrlRepository urlRepository;
    private final UrlUtil urlUtil;

    @Value("${shortenerHost}")
    private String shortenerHost;

    public UrlService(UrlRepository urlRepository, UrlUtil urlUtil) {
        this.urlRepository = urlRepository;
        this.urlUtil = urlUtil;
    }

    public ShortUrlDto shortenUrl(String longUrl, boolean flag) {
        if(urlUtil.isShortUrlLonger(longUrl) && flag == false){
            throw new InvalidUrlException("URL too short to shorten. Add '?flag=true' to proceed.");
        }

        String validUrl = urlUtil.checkUrl(longUrl);
        Url url = saveUrl(validUrl);
        return ShortUrlDto.builder()
                .short_url(url.getShort_url())
                .created_at(url.getCreated_at())
                .original_url(validUrl)
                .build();
    }


    public String getOriginalUrl(String shortUrl) {
        Url retrieveUrl = urlRepository.findById(shortUrl).orElseThrow();
        return retrieveUrl.getOriginal_url();
    }

    private Url saveUrl(String longUrl) {
        ObjectId newObjectId = new ObjectId();
        Url url = Url.builder()
                .id(newObjectId)
                .original_url(longUrl)
                .short_url(shortenerHost + newObjectId)
                .created_at(Instant.now())
                .build();
        urlRepository.save(url);
        return url;
    }


}
