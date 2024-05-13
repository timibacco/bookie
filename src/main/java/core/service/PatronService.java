package core.service;

public interface PatronService {


    void createPatron(String name, String email, String phone);

    void deletePatron(Long patronId);

    Object getPatron(Long patronId);

    Object getAllPatrons(); //todo: make pageable

}
