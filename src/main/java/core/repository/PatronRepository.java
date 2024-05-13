package core.repository;


import core.entity.Patron;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PatronRepository extends JpaRepository<Patron, Long> {

    Optional<Patron> findByEmail(String email);

    Object findByPatronId(Long patronId);

    Object findByName(String name);

    Object findByPhone(String phone);
}
