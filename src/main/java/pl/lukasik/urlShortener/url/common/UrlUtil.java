package pl.lukasik.urlShortener.url.common;


import org.apache.commons.validator.routines.UrlValidator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import pl.lukasik.urlShortener.url.exception.InvalidUrlException;
import pl.lukasik.urlShortener.url.exception.UrlException;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;


@Component
public class UrlUtil {

    @Value("${shortenerHost}")
    private String shortenerHost;
    @Value("${shortenedLength}")
    private int shortenedLength;


    public String checkUrl(String longUrl) {
        if (isUrlValid(longUrl)) {
            return longUrl;
        }

        try {
            return usesHttp(longUrl) ? "http://" + longUrl : "https://" + longUrl;
        } catch (IOException e) {
            throw new InvalidUrlException("Host is currently not reachable");
        }

    }

    private boolean usesHttp(String longUrl) throws IOException {
        URL httpsUrl = new URL("https://" + longUrl);
        HttpURLConnection httpsConnection = (HttpURLConnection) httpsUrl.openConnection();
        try {
            httpsConnection.connect();
            return false;
        } catch (IOException e) {
            URL httpUrl = new URL("http://" + longUrl);
            HttpURLConnection httpConnection = (HttpURLConnection) httpUrl.openConnection();
            httpConnection.connect();
            return true;
        } finally {
            httpsConnection.disconnect();
        }
    }

    private boolean isUrlValid(String longUrl) {
        String[] schemes = {"http", "https"};
        UrlValidator urlValidator = new UrlValidator(schemes);
        return urlValidator.isValid(longUrl);
    }

    public boolean isShortUrlLonger(String longUrl){
        int shortUrlLength = shortenerHost.length() + shortenedLength;
        int longUrlLength = longUrl.length();
        return shortUrlLength >= longUrlLength;
    }





}

