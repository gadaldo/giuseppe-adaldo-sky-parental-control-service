package sky.test.parental.control.service;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import sky.test.parental.control.service.exception.TechnicalFailureException;
import sky.test.parental.control.service.exception.TitleNotFoundException;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ParentalControlServiceImplTest {

    @Rule
    public ExpectedException ee = ExpectedException.none();

    @Mock
    private MovieService movieService;

    @InjectMocks
    private ParentalControlServiceImpl testObj;

    @Before
    public void setup() throws TechnicalFailureException, TitleNotFoundException {
        when(movieService.getParentalControlLevel("18 allowed movie")).thenReturn("18");
        when(movieService.getParentalControlLevel("15 allowed movie")).thenReturn("15");
        when(movieService.getParentalControlLevel("12 allowed movie")).thenReturn("12");
        when(movieService.getParentalControlLevel("PG allowed movie")).thenReturn("PG");
        when(movieService.getParentalControlLevel("U allowed movie")).thenReturn("U");
        when(movieService.getParentalControlLevel("title not found")).thenThrow(new TitleNotFoundException("Movie not found with id='Title Not Found'"));
        when(movieService.getParentalControlLevel("technical error")).thenThrow(new TechnicalFailureException("Unable to retrieve movie information"));
    }

    @Test
    public void shouldAllowToWatchMovieWhenCustomerParentalLevelIsGreaterOrEqualThanMovieParentalLevel() throws TechnicalFailureException, TitleNotFoundException {
        assertTrue(testObj.isAllowedToWatch("18", "18 allowed movie"));
        assertTrue(testObj.isAllowedToWatch("18", "15 allowed movie"));
        assertTrue(testObj.isAllowedToWatch("18", "12 allowed movie"));
        assertTrue(testObj.isAllowedToWatch("18", "U allowed movie"));
        assertTrue(testObj.isAllowedToWatch("18", "PG allowed movie"));

        assertTrue(testObj.isAllowedToWatch("15", "15 allowed movie"));
        assertTrue(testObj.isAllowedToWatch("15", "12 allowed movie"));
        assertTrue(testObj.isAllowedToWatch("15", "U allowed movie"));
        assertTrue(testObj.isAllowedToWatch("15", "PG allowed movie"));

        assertTrue(testObj.isAllowedToWatch("12", "12 allowed movie"));
        assertTrue(testObj.isAllowedToWatch("12", "U allowed movie"));
        assertTrue(testObj.isAllowedToWatch("12", "PG allowed movie"));

        assertTrue(testObj.isAllowedToWatch("PG", "PG allowed movie"));
        assertTrue(testObj.isAllowedToWatch("PG", "U allowed movie"));

        assertTrue(testObj.isAllowedToWatch("U", "U allowed movie"));

        verify(movieService, times(1)).getParentalControlLevel("18 allowed movie");
        verify(movieService, times(2)).getParentalControlLevel("15 allowed movie");
        verify(movieService, times(3)).getParentalControlLevel("12 allowed movie");
        verify(movieService, times(4)).getParentalControlLevel("PG allowed movie");
        verify(movieService, times(5)).getParentalControlLevel("U allowed movie");
        verify(movieService, times(15)).getParentalControlLevel(anyString());
    }

    @Test
    public void shouldNotAllowToWatchMovieWhenCustomerParentalLevelIsLessThanMovieParentalLevel() throws TechnicalFailureException, TitleNotFoundException {
        assertFalse(testObj.isAllowedToWatch("15", "18 allowed movie"));

        assertFalse(testObj.isAllowedToWatch("12", "18 allowed movie"));
        assertFalse(testObj.isAllowedToWatch("12", "15 allowed movie"));

        assertFalse(testObj.isAllowedToWatch("PG", "18 allowed movie"));
        assertFalse(testObj.isAllowedToWatch("PG", "15 allowed movie"));
        assertFalse(testObj.isAllowedToWatch("PG", "12 allowed movie"));

        assertFalse(testObj.isAllowedToWatch("U", "18 allowed movie"));
        assertFalse(testObj.isAllowedToWatch("U", "15 allowed movie"));
        assertFalse(testObj.isAllowedToWatch("U", "12 allowed movie"));
        assertFalse(testObj.isAllowedToWatch("U", "PG allowed movie"));

        verify(movieService, times(10)).getParentalControlLevel(anyString());
    }

    @Test
    public void shouldThrowTitleNotFoundException() throws TechnicalFailureException, TitleNotFoundException {
        ee.expect(TitleNotFoundException.class);
        ee.expectMessage("Movie not found with id='Title Not Found'");

        testObj.isAllowedToWatch("18", "title not found");
        verify(movieService, times(1)).getParentalControlLevel("title not found");
    }

    @Test
    public void shouldThrowTechnicalFailureException() throws TechnicalFailureException, TitleNotFoundException {
        ee.expect(TechnicalFailureException.class);
        ee.expectMessage("Unable to retrieve movie information");

        testObj.isAllowedToWatch("18", "technical error");
        verify(movieService, times(1)).getParentalControlLevel("technical error");
    }

    @Test
    public void shouldThrowTechnicalFailureExceptionWhenParentalLevelIsNotCorrect() throws TechnicalFailureException, TitleNotFoundException {
        ee.expect(TechnicalFailureException.class);
        ee.expectMessage("Bad parental control level='20'");

        testObj.isAllowedToWatch("20", "technical error");
        verify(movieService, never()).getParentalControlLevel(anyString());
    }

    @Test
    public void shouldThrowTechnicalFailureExceptionWhenMovieServiceReturnsBadParentalLevel() throws TechnicalFailureException, TitleNotFoundException {
        ee.expect(TechnicalFailureException.class);
        ee.expectMessage("Bad parental control level received for='bad parental control received'");

        when(movieService.getParentalControlLevel("bad parental control received")).thenReturn("20");
        testObj.isAllowedToWatch("18", "bad parental control received");
        verify(movieService, times(1)).getParentalControlLevel("bad parental control received");
    }
}
