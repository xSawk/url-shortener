package pl.lukasik.urlShortener.url.common;


import org.apache.commons.validator.routines.UrlValidator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import pl.lukasik.urlShortener.url.exception.InvalidUrlException;
import pl.lukasik.urlShortener.url.exception.UrlException;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


@Component
public class UrlUtil {


    @Value("${shortenerHost}")
    private String shortenerHost;
    @Value("${shortenedLength}")
    private int shortenedLength;
    private final String HTTP = "http://";
    private final String HTTPS = "https://";


    public String checkUrl(String longUrl) {
        if (isUrlValid(longUrl))  {
            return longUrl;
        }

        try {
            return usesHttp(longUrl) ? HTTP + longUrl : HTTPS + longUrl;
        } catch (IOException e) {
            throw new InvalidUrlException("Host is currently not reachable");
        }
    }

    private boolean usesHttp(String longUrl) throws IOException {
        URL httpsUrl = new URL(HTTPS + longUrl);
        HttpURLConnection httpsConnection = (HttpURLConnection) httpsUrl.openConnection();
        httpsConnection.setConnectTimeout(3000);
        try {
            httpsConnection.connect();
            return false;
        } catch (IOException e) {
            URL httpUrl = new URL(HTTP + longUrl);
            HttpURLConnection httpConnection = (HttpURLConnection) httpUrl.openConnection();
            httpConnection.setConnectTimeout(3000);
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

