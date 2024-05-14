package core.bookie.repository;


import core.bookie.entity.BorrowingRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InventoryRepository extends JpaRepository<BorrowingRecord, Long > {



}
