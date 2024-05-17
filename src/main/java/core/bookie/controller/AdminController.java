package core.bookie.controller;


import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

// ... other imports ...

@Slf4j
@RestController
@RequestMapping("/api/admin")
public class AdminController {

    @Secured("ROLE_ADMIN")
    @GetMapping("/test")
    public Object test() throws Exception {
        return "Hello Admin";
    }

}
