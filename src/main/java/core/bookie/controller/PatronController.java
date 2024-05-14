package core.bookie.controller;


import core.bookie.request.PatronRequest;
import core.bookie.service.PatronService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/patrons")
@RequiredArgsConstructor
public class PatronController {

    @Autowired
    private final PatronService patronService;


    @Operation(summary = "Add a new patron", description = "Add a new patron to the system",
            tags = {"patrons"},

            responses = {
                    @ApiResponse(responseCode = "200", description = "Patron added successfully!"),
                    @ApiResponse(responseCode = "500", description = "Internal server error")
            })
    @PostMapping("/add")
    public ResponseEntity<?> addNewPatron(@RequestBody PatronRequest patronRequest) {

        patronService.createPatron(patronRequest);
        return ResponseEntity.ok("Patron added successfully!");
    }


    @GetMapping("/{patronId}")
    public ResponseEntity<?> getPatron(@PathVariable Long patronId) {

        return ResponseEntity.ok(patronService.getPatron(patronId));
    }







    @Operation(summary = "Delete a patron", description = "Delete a patron from the system",
            tags = {"patrons"},

            responses = {

                    @ApiResponse(responseCode = "200", description = "Patron deleted successfully!"),
                    @ApiResponse(responseCode = "500", description = "Internal server error")
            })
    @DeleteMapping("/{patronId}")
    public ResponseEntity<?> deletePatron(@PathVariable Long patronId) {

        patronService.deletePatron(patronId);
        return ResponseEntity.ok("Patron deleted successfully!");
    }






}

