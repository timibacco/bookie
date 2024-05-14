package core.bookie.service;

import core.bookie.request.PatronRequest;
import org.springframework.data.domain.Pageable;

public interface PatronService {


    void createPatron(PatronRequest request);

    void deletePatron(Long patronId);

    Object getPatron(Long patronId);

    Object getAllPatrons(Pageable pageable); //todo: make pageable

}
