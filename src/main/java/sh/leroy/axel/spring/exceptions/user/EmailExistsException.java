package sh.leroy.axel.spring.exceptions.user;

public class EmailExistsException extends RuntimeException {
    public EmailExistsException(String message) {
        super(message);
    }
}
