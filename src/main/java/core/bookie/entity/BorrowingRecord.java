package core.bookie.entity;


import lombok.*;

import jakarta.persistence.*;


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


    @ManyToMany(cascade = CascadeType.ALL)
    private Collection<Book> book;


    @ManyToMany(cascade = CascadeType.ALL)
    private Collection<Patron> patron;

    @Temporal(TemporalType.DATE)
    private Date borrowDate;


    @Temporal(TemporalType.DATE)
    private Date returnDate;

    private boolean returned;

    private boolean overdue;






}
