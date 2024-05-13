package core.bookie.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PatronRequest {

    private String name;

    private String email;

    private String phone;

    private String password;


}
