package core.bookie.entity;


import core.bookie.utils.RoleName;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;

@Entity
@Getter
@Setter
@RequiredArgsConstructor
public class Role implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id ;

    @Enumerated(EnumType.STRING)
    private RoleName roleName ;

    public Role (RoleName roleName) {this.roleName = roleName;}

    public String getRoleName() {

        return roleName.toString();

}

}
