package sh.leroy.axel.spring.validation.username;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UsernameValidator
        implements ConstraintValidator<ValidUsername, String> {
    private Pattern pattern;
    private Matcher matcher;
    private static final String USERNAME_PATTERN = "^[a-zA-Z0-9]{0,20}$";

    @Override
    public void initialize(ValidUsername validUsername) {}

    @Override
    public boolean isValid(String username, ConstraintValidatorContext constraintValidatorContext) {
        return validateUsername(username);
    }

    private boolean validateUsername(String username) {
        pattern = Pattern.compile(USERNAME_PATTERN);
        matcher = pattern.matcher(username);
        return matcher.matches();
    }
}
