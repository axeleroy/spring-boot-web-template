package sh.leroy.axel.spring.controllers.api;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import sh.leroy.axel.spring.exceptions.message.MessageIdMissmatchException;
import sh.leroy.axel.spring.exceptions.message.MessageNotFoundException;

import javax.validation.ConstraintViolationException;

@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler({ MessageNotFoundException.class })
    protected ResponseEntity<?> handleMessageNotFound(Exception ex, WebRequest request) {
        return handleExceptionInternal(ex, "Message not found", new HttpHeaders(), HttpStatus.NOT_FOUND, request);
    }

    @ExceptionHandler({ MessageIdMissmatchException.class, ConstraintViolationException.class,
            DataIntegrityViolationException.class })
    protected ResponseEntity<?> handledBadRequest(Exception ex, WebRequest request) {
        return handleExceptionInternal(ex, ex.getLocalizedMessage(), new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }

    @ExceptionHandler({ UsernameNotFoundException.class })
    protected ResponseEntity<?> handleUserNotFound(Exception ex, WebRequest request) {
        return handleExceptionInternal(ex, "User not found", new HttpHeaders(), HttpStatus.NOT_FOUND, request);
    }
}
