package core.service.serviceImpl;

import core.bookie.request.PatronRequest;
import core.entity.Patron;
import core.repository.PatronRepository;
import core.service.PatronService;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PatronServiceImpl implements PatronService {


    private PatronRepository patronRepository;


    @Override
    public void createPatron(PatronRequest request) {

         patronRepository.findByEmail(request.getEmail()).ifPresent(patron -> {
            throw new IllegalStateException("Patron with email already exists!");

        });


        Patron patron = new Patron();

        patron.setName(request.getName());

        patron.setEmail(request.getEmail());

        patron.setPhone(request.getPhone());

        // todo: add password hashing
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
    public Object getAllPatrons(Pageable pageable) {
        return patronRepository.findAll(pageable);
    }
}
