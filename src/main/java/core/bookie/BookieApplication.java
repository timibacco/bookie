package core.bookie;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Scanner;

@Slf4j
@SpringBootApplication

public class BookieApplication {


    private static final String CREATE_SUPERUSER_CMD = "--create-admin";

    public static void main(String[] args) {

        if (args.length > 0 && args[0].equalsIgnoreCase(CREATE_SUPERUSER_CMD)) {
            log.info("\n\n-----> Creating superuser <-----\n");

//            Scanner scanner = new Scanner(System.in);
//
//            System.out.println("Enter username: ");
//
//            String username = scanner.nextLine();
//
//            System.out.println("Enter password: ");
//
//            String password = scanner.nextLine();
//
//            System.out.println("Enter email: ");
//
//            String email = scanner.nextLine();
//
//            scanner.close();

            System.out.println("\n\n----> Superuser created successfully! <----");
        }




        SpringApplication.run(BookieApplication.class, args);
    }

}
