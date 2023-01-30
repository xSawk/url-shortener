package pl.lukasik.urlShortener.url.common;


import org.apache.commons.validator.routines.UrlValidator;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.SQLOutput;

@Component
public class UrlUtil {

    public String checkUrl(String longUrl) throws IOException {

        if(isUrlValid(longUrl)) {
            return longUrl;
        }

        if(usesHttp(longUrl)){
            return "http://"+longUrl;
        } else {
            return "https://"+longUrl;
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





}

