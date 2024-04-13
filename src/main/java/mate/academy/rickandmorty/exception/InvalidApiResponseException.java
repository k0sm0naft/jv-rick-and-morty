package mate.academy.rickandmorty.exception;

public class InvalidApiResponseException extends RuntimeException {
    public InvalidApiResponseException(String message, Throwable cause) {
        super(message, cause);
    }
}
