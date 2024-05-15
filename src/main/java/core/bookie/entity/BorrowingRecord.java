package core.bookie.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import jakarta.persistence.*;


import java.time.Instant;
import java.util.Collection;
import java.util.Date;

@Data
@Entity
@RequiredArgsConstructor
@Table(name = "borrowing_record")
public class BorrowingRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long recordId;


    @ManyToOne(cascade = CascadeType.ALL)
    private Book book;


    @ManyToOne(cascade = CascadeType.ALL)
    private Patron patron;

    @Temporal(TemporalType.DATE)
    private Date borrowDate;


    @Temporal(TemporalType.DATE)
    private Date returnDate;

    private boolean returned;


    private boolean toBeFined;



    @JsonProperty("IsOverdue")
    public boolean isOverdue() {
        return Instant.now().isAfter(Instant.ofEpochMilli(returnDate.getTime()));
    }






}
