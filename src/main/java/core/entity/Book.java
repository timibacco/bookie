package core.entity;

import jakarta.annotation.Generated;
import jakarta.persistence.*;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.Date;

@Data
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

    private boolean available;


    public String toString() {
        return "Book{" +
                "bookId=" + bookId +
                ", title='" + title + '\'' +
                ", author='" + author + '\'' +
                ", publicationDate=" + publicationDate +
                ", ISBN='" + ISBN + '\'' +
                ", edition=" + edition + "th"+ '\'' +
                ", available=" + available +
                '}';
    }



}
