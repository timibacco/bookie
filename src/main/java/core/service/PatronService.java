package core.service;

import org.springframework.data.domain.Pageable;

public interface PatronService {


    void createPatron(String name, String email, String phone);

    void deletePatron(Long patronId);

    Object getPatron(Long patronId);

    Object getAllPatrons(Pageable pageable); //todo: make pageable

}
