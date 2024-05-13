package core.repository;


import core.entity.Patron;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface patronRepo extends JpaRepository<Patron, Long> {

    Object findByEmail(String email);

    Object findByPatronId(Long patronId);

    Object findByName(String name);

    Object findByPhone(String phone);
}
