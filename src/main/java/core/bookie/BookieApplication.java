package core.bookie;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Scanner;

@SpringBootApplication
public class BookieApplication {


    private static final String CREATE_SUPERUSER_CMD = "createsuperuser";

    public static void main(String[] args) {

        if (args.length > 0 && args[0].equals(CREATE_SUPERUSER_CMD)) {
            System.out.println("Creating superuser...");

            Scanner scanner = new Scanner(System.in);

            System.out.println("Enter username: ");

            String username = scanner.nextLine();

            System.out.println("Enter password: ");

            String password = scanner.nextLine();

            System.out.println("Enter email: ");

            String email = scanner.nextLine();

            scanner.close();

            System.out.println("Superuser created successfully!");
        }




        SpringApplication.run(BookieApplication.class, args);
    }

}
