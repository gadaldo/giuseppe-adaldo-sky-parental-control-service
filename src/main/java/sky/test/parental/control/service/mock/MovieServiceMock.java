package sky.test.parental.control.service.mock;

import sky.test.parental.control.service.MovieService;
import sky.test.parental.control.service.exception.TechnicalFailureException;
import sky.test.parental.control.service.exception.TitleNotFoundException;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.function.BiPredicate;
import java.util.stream.Collectors;

import static sky.test.parental.control.service.ParentalControlLevel.getValueOf;

public class MovieServiceMock implements MovieService {

    private static final BiPredicate<String, String> movieFilter = (current, movieId) -> current.toLowerCase().contains(movieId.toLowerCase());

    @Override
    public String getParentalControlLevel(String movieId) throws TitleNotFoundException, TechnicalFailureException {
        try (BufferedReader buffer = new BufferedReader(new InputStreamReader(this.getClass().getResourceAsStream("/movies-db.txt")))) {
            final String movieInfo = buffer.lines()
                    .filter(current -> movieFilter.test(current, movieId))
                    .collect(Collectors.joining());

            if (movieInfo.isEmpty())
                throw new TitleNotFoundException("Movie not found with id=" + movieId);

            return getValueOf(movieInfo.split("=")[1]).value();
        } catch (TitleNotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new TechnicalFailureException(e.getMessage());
        }
    }

    public static void main(String[] args) throws TechnicalFailureException, TitleNotFoundException {
        MovieService m = new MovieServiceMock();
        System.out.println(m.getParentalControlLevel("In the fade"));
    }

}