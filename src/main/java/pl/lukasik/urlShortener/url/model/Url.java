package pl.lukasik.urlShortener.url.model;

import lombok.*;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Document
public class Url {

    @Id
    private ObjectId _id;
    private String original_url;
    private String short_url;
    private Date created_at;
}
