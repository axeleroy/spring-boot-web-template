package sh.leroy.axel.spring.exceptions.message;

public class MessageNotFoundException extends RuntimeException {
    public MessageNotFoundException(long id) {
        super("Message ID " + id + "not found.");
    }
}
