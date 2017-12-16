package sh.leroy.axel.spring.exceptions.user;

public class UsernameExistsException extends RuntimeException {
    public UsernameExistsException(String message) {
        super(message);
    }
}
