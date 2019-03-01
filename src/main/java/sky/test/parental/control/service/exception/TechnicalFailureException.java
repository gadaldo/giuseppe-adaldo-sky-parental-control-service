package sky.test.parental.control.service.exception;

public class TechnicalFailureException extends Exception {
    private static final long serialVersionUID = -8663461297794295451L;

    public TechnicalFailureException(String message) {
        super(message);
    }
}
