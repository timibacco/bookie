package core.bookie;

import core.bookie.entity.Patron;
import core.bookie.entity.Role;
import core.bookie.utils.RoleName;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.GrantedAuthority;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PatronTest {

    private Patron patron;

    @BeforeEach
    void setUp() {
        patron = new Patron();
        List<Role> roles = new ArrayList<>();

    }

    @Test
    void getAuthorities_returnsCorrectAuthorities_whenRolesArePresent() {
        Role role1 = new Role();
        role1.setRoleName(RoleName.valueOf("ADMIN"));
        Role role2 = new Role();
        role2.setRoleName(RoleName.valueOf("USER"));

        patron.setRoles(Arrays.asList(role1, role2));

        Collection<? extends GrantedAuthority> authorities = patron.getAuthorities();

        assertEquals(2, authorities.size());
        assertTrue(authorities.stream().anyMatch(a -> a.getAuthority().equals("ADMIN")));
        assertTrue(authorities.stream().anyMatch(a -> a.getAuthority().equals("USER")));
    }

    @Test
    void getAuthorities_returnsEmptyCollection_whenNoRolesArePresent() {
        patron.setRoles(List.of());
        Collection<? extends GrantedAuthority> authorities = patron.getAuthorities();

        assertTrue(authorities.isEmpty());
    }


}