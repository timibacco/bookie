package core.bookie.repository;


import core.bookie.entity.Role;
import core.bookie.utils.RoleName;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long>{


    Role findByRoleName(RoleName roleName);


}
