package mate.academy.internet.shop.exception;

public class DataProcessingException extends RuntimeException {
    public DataProcessingException(Throwable cause) {
        super(cause);
    }

    public DataProcessingException(String message, Throwable cause) {
        super(message, cause);
    }
}
