package core.bookie.controller;


import core.bookie.request.LoginRequest;
import core.bookie.request.PatronRequest;
import core.bookie.service.serviceImpl.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {


    @Autowired
    private final AuthService authService;


    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequest loginRequest) {

        try {
            return authService.login(loginRequest);
        } catch (Exception e) {
            return ResponseEntity.status(500).body("An error occurred");
        }
    }

    @PostMapping("/create")
    public ResponseEntity<?> createAdmin(@Valid @RequestBody PatronRequest request) {
        try {
            return authService.createPatronByAdmin(request);
        } catch (Exception e) {
            return ResponseEntity.status(500).body("An error occurred");
        }
    }

}
