package core.service.serviceImpl;

import core.entity.Patron;
import core.repository.PatronRepository;
import core.service.PatronService;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PatronServiceImpl implements PatronService {


    private PatronRepository patronRepository;


    @Override
    public void createPatron(String name, String email, String phone) {

         patronRepository.findByEmail(email).ifPresent(patron -> {
            throw new IllegalStateException("Patron with email already exists!");

        });


        Patron patron = new Patron();

        patron.setName(name);

        patron.setEmail(email);

        patron.setPhone(phone);

        patronRepository.saveAndFlush(patron);


    }

    @Override
    public void deletePatron(Long patronId) {

    }

    @Override
    public Object getPatron(Long patronId) {
        return patronRepository.findByPatronId(patronId);
    }

    @Override
    public Object getAllPatrons() {
        return patronRepository.findAll();
    }
}
