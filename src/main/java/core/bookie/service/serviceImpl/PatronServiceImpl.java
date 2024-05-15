package core.bookie.service.serviceImpl;

import core.bookie.entity.Role;
import core.bookie.repository.RoleRepository;
import core.bookie.request.PatronRequest;
import core.bookie.entity.Patron;
import core.bookie.repository.PatronRepository;
import core.bookie.service.PatronService;
import core.bookie.utils.RoleName;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


import java.util.Collections;


@Service
@RequiredArgsConstructor
public class PatronServiceImpl implements PatronService {

    @Autowired
    private final PasswordEncoder encoder = new BCryptPasswordEncoder();

    @Autowired
    private final PatronRepository patronRepository;

    @Autowired
    private RoleRepository roleRepository;

    private static final String MY_ROLE = "USER";
    @Override
    public void createPatron(PatronRequest request) {

         patronRepository.findByEmail(request.getEmail()).ifPresent(patron -> {
            throw new IllegalStateException("Patron with email already exists!");
            //todo: define custom exceptions

        });


        Patron patron = new Patron();

        patron.setName(request.getName());

        patron.setEmail(request.getEmail());

        patron.setPhone(request.getPhone());

        patron.setPassword(encoder
                .encode(request.getPassword()));


        Role role = roleRepository.findByRoleName(RoleName.valueOf(MY_ROLE));


        patron.setRoles(Collections.singletonList(role));


        patronRepository.saveAndFlush(patron);


    }

    @Override
    public void deletePatron(Long patronId) {

        patronRepository.deleteById(patronId);

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
