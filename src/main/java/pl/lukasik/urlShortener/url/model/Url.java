package pl.lukasik.urlShortener.url.model;

import lombok.*;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;
import java.util.Date;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Document
public class Url {

    @Id
    private ObjectId id;
    private String original_url;
    private String short_url;
    private Instant created_at;
}
