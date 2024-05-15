package core.bookie.repository;


import core.bookie.entity.Book;
import core.bookie.entity.BorrowingRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface InventoryRepository extends JpaRepository<BorrowingRecord, Long > {


    Optional<BorrowingRecord> findByBook(Book book);


}
