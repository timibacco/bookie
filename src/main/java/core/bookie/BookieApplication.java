package core.bookie;

import core.bookie.entity.Role;
import core.bookie.repository.RoleRepository;
import core.bookie.utils.MockPopulator;
import core.bookie.utils.RoleName;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Scanner;

@Slf4j
@SpringBootApplication

public class BookieApplication {


    @Autowired
    private MockPopulator mockPopulator;



    public static void main(String[] args) {


        SpringApplication.run(BookieApplication.class, args);
    }

    @Bean
    CommandLineRunner run() throws NoSuchMethodException {


        return args -> {



            mockPopulator.createAdminOnCLI(args);



            log.info("\n\n\tApplication started successfully!\n\n\t");
        };
    }

}
