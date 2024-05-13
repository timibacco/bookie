package core.entity;


import lombok.*;

import jakarta.persistence.*;


import java.util.Date;

@Data
@RequiredArgsConstructor
@Table(name = "borrowing_record")
public class BorrowingRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long recordId;


    @ManyToMany(cascade = CascadeType.ALL)
    private Book book;


    @ManyToMany(cascade = CascadeType.ALL)
    private Patron patron;

    @Temporal(TemporalType.DATE)
    private Date borrowDate;


    @Temporal(TemporalType.DATE)
    private Date returnDate;

    private boolean returned;

    private boolean overdue;






}
