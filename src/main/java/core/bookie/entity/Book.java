package core.bookie.entity;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.annotation.Generated;
import jakarta.persistence.*;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;

@Data
@Entity
@RequiredArgsConstructor
@Table(name = "books")
public class Book implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long bookId;

    private String title;

    private String author;

    @Temporal(TemporalType.DATE)
    private Date publicationDate;

    private String ISBN;

    private Integer edition;

    private long quantity;

    @JsonProperty(value = "IsAvailable")
    public boolean isAvailable(){
        return quantity > 1;
    }


    @JsonGetter("edition")
    public String getEditionWithSuffix() {
        String suffix = switch (edition) {
            case 1 -> "st";
            case 2 -> "nd";
            case 3 -> "rd";
            default -> "th";
        };

        return edition + suffix + " Edition";
    }





}
