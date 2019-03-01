package sky.test.parental.control.service;

import sky.test.parental.control.service.exception.TechnicalFailureException;
import sky.test.parental.control.service.exception.TitleNotFoundException;

public interface ParentalControlService {

    /**
     * Return true if the parental control level of the movie is equal to or less than the customer’s preference, false otherwise.
     *
     * @param customerParentalControlLevel
     * @param movieId
     * @return
     * @throws TechnicalFailureException in case there is any technical problem retrieving movie information
     * @throws TitleNotFoundException    in case the movie is not in the database
     */
    boolean isAllowedToWatch(String customerParentalControlLevel, String movieId) throws TitleNotFoundException, TechnicalFailureException;
}
