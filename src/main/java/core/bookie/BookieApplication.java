package core.bookie;

import core.bookie.entity.Role;
import core.bookie.repository.RoleRepository;
import core.bookie.utils.RoleName;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Scanner;

@Slf4j
@SpringBootApplication

public class BookieApplication {


    private static final String CREATE_SUPERUSER_CMD = "--create-admin";

    public static void main(String[] args) {


        SpringApplication.run(BookieApplication.class, args);
    }

    @Bean
    CommandLineRunner run() throws NoSuchMethodException {


        return args -> {


            log.info (args[0]);

            log.info("\n\n\tApplication started successfully!\n\n\t");
        };
    }

}
