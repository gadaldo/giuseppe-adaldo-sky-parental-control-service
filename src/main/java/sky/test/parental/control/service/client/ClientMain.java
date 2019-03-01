package sky.test.parental.control.service.client;

import sky.test.parental.control.service.MovieService;
import sky.test.parental.control.service.ParentalControlService;
import sky.test.parental.control.service.ParentalControlServiceImpl;
import sky.test.parental.control.service.exception.TechnicalFailureException;
import sky.test.parental.control.service.exception.TitleNotFoundException;
import sky.test.parental.control.service.mock.MovieServiceMock;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.stream.Collectors;

public class ClientMain {

    public static void main(String[] args) throws IOException {
        if (args != null && args.length == 1) {
            switch (args[0]) {
                case "help":
                    System.out.println("specify \"customerParentalControlLevel\" with quotes in case of spaces and movieId");
                    System.out.println("specify catalogue to get movies's catalogue");
                    break;
                case "catalogue":
                    try (BufferedReader buffer = new BufferedReader(new InputStreamReader(ClientMain.class.getResourceAsStream("/movies-db.txt")))) {
                        String movies = buffer.lines().collect(Collectors.joining("\n"));
                        System.out.println("chose a movie between:");
                        System.out.println(movies);
                    }

                    System.out.println("chose a parental control level between: U, PG, 12, 15 and 18");
                    break;
                default:
                    System.out.println("Invalid input");
                    break;
            }
            System.exit(0);
        }
        if (args == null || args.length != 2) {
            System.out.println("you need to specify 2 arguments: \"customerParentalControlLevel\" with quotes in case of spaces and movieId");
            System.exit(-1);
        }

        MovieService movieService = new MovieServiceMock();
        ParentalControlService parentalControlService = new ParentalControlServiceImpl(movieService);

        try {
            boolean isAllowed = parentalControlService.isAllowedToWatch(args[0], args[1]);
            if (isAllowed)
                System.out.println("The user is allowed to watch the movie");
            else
                System.out.println("The user is NOT allowed to watch the movie");
        } catch (TitleNotFoundException | TechnicalFailureException e) {
            System.out.println(e.getMessage());
        }
    }
}
