package core.bookie.service.serviceImpl;


import core.bookie.entity.Patron;
import core.bookie.entity.Role;
import core.bookie.repository.PatronRepository;
import core.bookie.repository.RoleRepository;
import core.bookie.request.LoginRequest;
import core.bookie.request.PatronRequest;
import core.bookie.security.TokenService;
import core.bookie.utils.RoleName;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
@RequiredArgsConstructor
public class AuthService {


    private static final String MY_ROLE = "ADMIN";

    @Autowired
    private final PasswordEncoder encoder = new BCryptPasswordEncoder();

    @Autowired
    private final TokenService tokenService;

    @Autowired
    private final RoleRepository roleRepository;
    @Autowired
    private final PatronRepository patronRepository;

    @Autowired
    private final AuthenticationManager authenticationManager;


    public ResponseEntity<?> login(LoginRequest loginRequest) {

        var patron = patronRepository.findByEmail(loginRequest.getUsername());


        if (patron.isEmpty()) {
            return new ResponseEntity<>("Invalid credentials", HttpStatus.UNAUTHORIZED);
        }


        if (!encoder.matches(loginRequest.getPassword(), patron.get().getPassword())) {
            return new ResponseEntity<>("Invalid credentials", HttpStatus.UNAUTHORIZED);
        }


        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
        var token = tokenService.generateToken(patron.get());

        SecurityContextHolder.getContext().setAuthentication(authentication);

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + token);

        return new ResponseEntity<>(headers, HttpStatus.OK);



    }

    public ResponseEntity<?> createPatronByAdmin(PatronRequest request) {

        patronRepository.findByEmail(request.getEmail()).ifPresent(lol -> {
            throw new IllegalStateException("Patron with email already exists!");
        });

            Patron patron = new Patron();

            patron.setName(request.getName());

            patron.setEmail(request.getEmail());

            patron.setPhone(request.getPhone());

            patron.setPassword(encoder
                    .encode(request.getPassword()));

            Role role = roleRepository.findByRoleName(RoleName.valueOf(MY_ROLE));

            patron.setRoles(Collections.singletonList(role));

            patronRepository.save(patron);

            return new ResponseEntity<>("Admin created successfully", HttpStatus.CREATED);
    }

}
