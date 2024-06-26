package core.bookie.service.serviceImpl;

import core.bookie.entity.Role;
import core.bookie.repository.RoleRepository;
import core.bookie.request.PatronRequest;
import core.bookie.entity.Patron;
import core.bookie.repository.PatronRepository;
import core.bookie.service.PatronService;
import core.bookie.utils.RoleName;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import core.bookie.utils.utils;
import java.util.Collections;
import java.util.Map;


@Service
@RequiredArgsConstructor
public class PatronServiceImpl implements PatronService {


    @Autowired
    private  utils utils;


    @Autowired
    private  final PasswordEncoder encoder = new BCryptPasswordEncoder();

    @Autowired
    private  PatronRepository patronRepository;

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



    @Override
    public Object updatePatron(Long patronID, Map<Object, Object> fields){

        var patronOptional = patronRepository.findById(patronID);

        if(patronOptional.isEmpty()){
            throw new IllegalStateException("Patron with id " + patronID + " does not exist!");
        }

        Patron patron = patronOptional.get();

        fields.forEach((k, v) -> {
            if (v != null) {
                switch (k.toString()) {
                    case "name":
                        patron.setName((String) v);
                        break;
                    case "email":
                        patron.setEmail((String) v);
                        break;
                    case "phone":
                        patron.setPhone((String) v);
                        break;
                }

            }
        });

        return patronRepository.saveAndFlush(patron);
    }
}
