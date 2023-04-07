package pl.lukasik.urlShortener.url.service;


import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.lukasik.urlShortener.url.common.UrlUtil;
import pl.lukasik.urlShortener.url.exception.ErrorMessages;
import pl.lukasik.urlShortener.url.exception.InvalidUrlException;
import pl.lukasik.urlShortener.url.model.Url;
import pl.lukasik.urlShortener.url.model.dto.ShortUrlDto;
import pl.lukasik.urlShortener.url.repository.UrlRepository;

import java.time.Instant;
import java.util.concurrent.atomic.AtomicInteger;


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

    public ShortUrlDto shortenUrl(String longUrl, boolean allowShorting) {
        if (urlUtil.isShortUrlLonger(longUrl) && !allowShorting) {
            throw new InvalidUrlException(ErrorMessages.URL_TOO_SHORT_ERROR_MESSAGE);
        }

        String validUrl = urlUtil.checkUrl(longUrl);
        Url url = saveUrl(validUrl);
        return ShortUrlDto.builder()
                .short_url(url.getShort_url())
                .created_at(url.getCreated_at())
                .original_url(validUrl)
                .build();
    }

    @Transactional
    public String getOriginalUrl(String id) {
        Url originalUrl = urlRepository.findById(id).orElseThrow();
        increaseTotalVisits(originalUrl);
        return originalUrl.getOriginal_url();
    }

    private Url saveUrl(String longUrl) {
        ObjectId newObjectId = new ObjectId();
        Url url = Url.builder()
                .id(newObjectId)
                .original_url(longUrl)
                .short_url(shortenerHost + newObjectId)
                .created_at(Instant.now())
                .totalVisits(new AtomicInteger(0))
                .build();
        urlRepository.save(url);
        return url;
    }

    private void increaseTotalVisits(Url originalUrl) {
        AtomicInteger totalVisits = originalUrl.getTotalVisits();
        totalVisits.getAndIncrement();
        originalUrl.setTotalVisits(totalVisits);
        urlRepository.save(originalUrl);
    }
}
