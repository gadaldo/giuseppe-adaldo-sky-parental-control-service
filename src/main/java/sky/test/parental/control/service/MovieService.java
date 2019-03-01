package sky.test.parental.control.service;

import sky.test.parental.control.service.exception.TechnicalFailureException;
import sky.test.parental.control.service.exception.TitleNotFoundException;

public interface MovieService {

    /**
     * Returns parental control information about given movieId.
     *
     * @param movieId
     * @return String such as 'U', 'PG', '12', '15', '18'
     * @throws TitleNotFoundException
     * @throws TechnicalFailureException
     */
    String getParentalControlLevel(String movieId)
            throws TitleNotFoundException,
            TechnicalFailureException;
}
