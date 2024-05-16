package core.bookie.utils;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import core.bookie.entity.Book;
import core.bookie.entity.BorrowingRecord;
import core.bookie.entity.Patron;
import core.bookie.entity.Role;
import core.bookie.repository.BooksRepository;
import core.bookie.repository.InventoryRepository;
import core.bookie.repository.PatronRepository;
import core.bookie.repository.RoleRepository;
import core.bookie.request.PatronRequest;
import core.bookie.service.serviceImpl.AuthService;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.InputStreamSource;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import javax.swing.*;
import java.io.BufferedInputStream;
import java.io.Console;
import java.io.IOException;
import java.io.InputStream;
import java.nio.Buffer;
import java.util.List;
import java.util.Scanner;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class MockPopulator {

    @Autowired
    private PatronRepository patronRepository;

    @Autowired
    private BooksRepository booksRepository;

    @Autowired
    private RoleRepository roleRepository;

    private final PasswordEncoder encoder = new BCryptPasswordEncoder();

    @Autowired
    private  InventoryRepository inventoryRepository;

    private static final String CREATE_SUPERUSER_CMD = "--create-admin";


    @Autowired
    private  final AuthService authservice;



    @PostConstruct
    public void populate() throws IOException {


        roleRepository.save(new Role(RoleName.USER));
        roleRepository.save(new Role (RoleName.ADMIN));

        ObjectMapper objectMapper = new ObjectMapper();

        TypeReference<List<Patron>> patronType = new TypeReference<List<Patron>>() {
        };


        try(
        BufferedInputStream resource0 = (BufferedInputStream) new ClassPathResource("patrons-mock.json").getInputStream()){

            BufferedInputStream resource1 = (BufferedInputStream) new ClassPathResource("books-mock.json").getInputStream();

            BufferedInputStream resource2 = (BufferedInputStream) new ClassPathResource("inventory-mock.json").getInputStream();

            List<Patron> patrons = objectMapper.readValue(resource0, patronType);
                List<Book> books = objectMapper.readValue(resource1, new TypeReference<>() {
                });
                List<BorrowingRecord> inventory = objectMapper.readValue(resource2, new TypeReference<>() {
                });


        for(Patron patron: patrons){
            patron.setPassword(encoder.encode(patron.getPassword()));
            log.info("\n-----> Populating mock data for patron entity <------\n");
        }
            patronRepository.saveAllAndFlush(patrons);

            log.info("\n-----> Mock data for patron entity populated successfully <------\n");

            booksRepository.saveAllAndFlush(books);

            log.info("\n-----> Mock data for books entity populated successfully <------\n");

            inventoryRepository.saveAllAndFlush(inventory);

            log.info("\n-----> Mock data for borrowingRecord entity populated successfully <------\n");


        }catch(IOException e){
            log.error("Error occurred while populating mock data for patron entity", e);
        }


    }

    public void createAdminOnCLI(String[] args) {

        if (args.length > 0 && args[0].equals(CREATE_SUPERUSER_CMD)) {
            log.info("Creating superuser...");


            String password;
            String email = "";
            Console console = System.console();
            if (console == null) {
            Scanner scanner = new Scanner(System.in);
            log.info("\n\t\tEnter superuser email: \t\t\n");
             email = scanner.nextLine();

              log.info("\n\t\tEnter superuser password: \t\t\n");
               password = scanner.nextLine();

               scanner.close();
            } else {
                password = String.valueOf(console.readPassword("Enter password: "));
            }


            PatronRequest in = new PatronRequest();
            in.setEmail(email);
            in.setPassword(password);

            authservice.createPatronByAdmin(in);

            log.info("\n\t\t------> Superuser created successfully! <------\n");
        }
    }



    }


