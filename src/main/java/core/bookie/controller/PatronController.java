package core.bookie.controller;


import core.bookie.request.PatronRequest;
import core.bookie.service.PatronService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

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


    @Operation(summary = "Get a patron", description = "Get a patron from the system",
            tags = {"patrons"},

            responses = {

                    @ApiResponse(responseCode = "200", description = "Patron retrieved successfully!"),
                    @ApiResponse(responseCode = "500", description = "Internal server error")
            })
    @GetMapping("/{patronId}")
    public ResponseEntity<?> getPatron(@PathVariable Long patronId) {

        return ResponseEntity.ok(patronService.getPatron(patronId));
    }



    @Operation(summary = "Update a patron", description = "Update a patron in the system",
            tags = {"patrons"},

            responses = {

                    @ApiResponse(responseCode = "200", description = "Patron updated successfully!"),
                    @ApiResponse(responseCode = "500", description = "Internal server error")
            })
    @PatchMapping("/{patronId}")
    public ResponseEntity<?> updatePatron(@PathVariable Long patronId, @RequestBody Map<Object, Object> patronRequest) {

        var response = patronService.updatePatron(patronId, patronRequest);
        return ResponseEntity.ok(response);
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



    @GetMapping("/all")
    public ResponseEntity<?> getAllPatrons(@PageableDefault(size = 10, page = 0) Pageable pageable){

        return ResponseEntity.ok(patronService.getAllPatrons(pageable));
    }






}

