package core.bookie.utils;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import core.bookie.entity.Patron;
import core.bookie.entity.Role;
import core.bookie.repository.BooksRepository;
import core.bookie.repository.InventoryRepository;
import core.bookie.repository.PatronRepository;
import core.bookie.repository.RoleRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.InputStreamSource;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.Buffer;
import java.util.List;

@Slf4j
@Configuration
public class MockPopulator {

    @Autowired
    private PatronRepository patronRepository;

    @Autowired
    private BooksRepository booksRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private  InventoryRepository inventoryRepository;

    @PostConstruct
    public void populate() throws IOException {


        roleRepository.save(new Role(RoleName.USER));
        roleRepository.save(new Role (RoleName.ADMIN));

        ObjectMapper objectMapper = new ObjectMapper();

        TypeReference<List<Patron>> patronType = new TypeReference<List<Patron>>() {
        };


        try(
        BufferedInputStream resource0 = (BufferedInputStream) new ClassPathResource("patrons-mock.json").getInputStream()){

        List<Patron> patrons = objectMapper.readValue(resource0, patronType);

            log.info("\n-----> Populating mock data for patron entity <------\n");

            patronRepository.saveAllAndFlush(patrons);

            log.info("\n-----> Mock data for patron entity populated successfully <------\n");


        }catch(IOException e){
            log.error("Error occurred while populating mock data for patron entity", e);
        }


    }

}
