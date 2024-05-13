package core.bookie.request;


import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class BookRequest {

    private String title;

    private String author;

    private Date publicationDate;

    private Integer edition;


}
