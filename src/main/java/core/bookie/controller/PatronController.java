package core.bookie.controller;


import core.bookie.request.PatronRequest;
import core.service.PatronService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/patrons")
public class PatronController {

    private PatronService patronService;


    @PostMapping("/add")
    public ResponseEntity<?> addNewPatron(@RequestBody PatronRequest patronRequest) {

        patronService.createPatron(patronRequest);
        return ResponseEntity.ok("Patron added successfully!");
    }
}
