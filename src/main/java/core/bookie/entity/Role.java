package core.bookie.entity;


import core.bookie.utils.RoleName;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;

import java.io.Serializable;

@Entity
@Getter
@Setter
@RequiredArgsConstructor
public class Role implements Serializable, GrantedAuthority {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id ;

    @Enumerated(EnumType.STRING)
    private RoleName roleName ;

    public Role (RoleName roleName) {this.roleName = roleName;}


    @Override
    public String getAuthority() {

        return roleName.toString();

}

}
