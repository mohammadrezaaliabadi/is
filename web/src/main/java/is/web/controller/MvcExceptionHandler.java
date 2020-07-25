package is.web.controller;

import javassist.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class MvcExceptionHandler {

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity validationErrorHandler(NotFoundException ex){
        return new ResponseEntity(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }
}
