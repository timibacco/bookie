package core.bookie;

import core.bookie.entity.Patron;
import core.bookie.repository.PatronRepository;
import core.bookie.service.PatronService;
import core.bookie.service.serviceImpl.PatronServiceImpl;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest
class BookieApplicationTests {


    @Mock
     PatronService patronService = new PatronServiceImpl();

    @Mock
     PatronRepository patronRepository;

    @Test
    void contextLoads() {
    }




    @Test
    public void test_updatePatron_throwsExceptionIfPatronDoesNotExist() {

        Long patronId = 1L;
        Map<Object, Object> fields = new HashMap<>();

        Optional<Patron> patronOptional = Optional.empty();
        when(patronRepository.findById(patronId)).thenReturn(patronOptional);


        assertThrows(IllegalStateException.class, () -> {
            patronService.updatePatron(patronId, fields);
        });
    }
}
