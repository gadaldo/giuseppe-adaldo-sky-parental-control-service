package sky.test.parental.control.service;

import sky.test.parental.control.service.exception.TechnicalFailureException;
import sky.test.parental.control.service.exception.TitleNotFoundException;

import static sky.test.parental.control.service.ParentalControlLevel.*;

public class ParentalControlServiceImpl implements ParentalControlService {

    private final MovieService movieService;

    public ParentalControlServiceImpl(MovieService movieService) {
        this.movieService = movieService;
    }

    public boolean isAllowedToWatch(String customerParentalControlLevel, String movieId) throws TitleNotFoundException, TechnicalFailureException {
        ParentalControlLevel customerParentalControlLevelInfo = getValueOf(customerParentalControlLevel);
        if (customerParentalControlLevelInfo == null)
            throw new TechnicalFailureException("Bad parental control level='" + customerParentalControlLevel + "'");

        ParentalControlLevel movieParentalControlLevelInfo = getValueOf(movieService.getParentalControlLevel(movieId));
        if (movieParentalControlLevelInfo == null)
            throw new TechnicalFailureException("Bad parental control level received for='" + movieId + "'");

        return customerParentalControlLevelInfo.ordinal() >= movieParentalControlLevelInfo.ordinal();
    }
}
