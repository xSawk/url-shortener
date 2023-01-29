package pl.lukasik.urlShortener.url.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import pl.lukasik.urlShortener.url.model.Url;

public interface UrlRepository extends MongoRepository<Url, String> {
}
