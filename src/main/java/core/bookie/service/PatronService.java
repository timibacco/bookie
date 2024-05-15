package core.bookie.service;

import core.bookie.request.PatronRequest;
import org.springframework.data.domain.Pageable;

import java.util.Map;

public interface PatronService {


    void createPatron(PatronRequest request);

    void deletePatron(Long patronId);

    Object getPatron(Long patronId);

    Object getAllPatrons(Pageable pageable);

    Object updatePatron(Long patronId, Map<Object, Object> fields);

}
