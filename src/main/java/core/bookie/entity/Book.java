package core.bookie.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.annotation.Generated;
import jakarta.persistence.*;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.Date;

@Data
@Entity
@RequiredArgsConstructor
@Table(name = "books")
public class Book {

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


    public String toString() {
        return "Book{" +
                "bookId=" + bookId +
                ", title='" + title + '\'' +
                ", author='" + author + '\'' +
                ", publicationDate=" + publicationDate +
                ", ISBN='" + ISBN + '\'' +
                ", edition=" + edition + "th"+ '\'' +
                ", available=" + isAvailable() +
                '}';
    }



}
