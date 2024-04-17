package mn.btv.api.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.format.DateTimeParseException;

@ControllerAdvice
public class ExceptionController {
    @ExceptionHandler(DateTimeParseException.class)
    public ResponseEntity<CustomError> handleDateTimeParseException(){
        CustomError error = new CustomError();
        error.setStatus(HttpStatus.BAD_REQUEST.value());
        error.setMessage("DateTime is invalid!");
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(NumberFormatException.class)
    public ResponseEntity<CustomError> handleNumberFormatException(){
        CustomError error = new CustomError();
        error.setStatus(HttpStatus.BAD_REQUEST.value());
        error.setMessage("ID is invalid!");
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }
}
