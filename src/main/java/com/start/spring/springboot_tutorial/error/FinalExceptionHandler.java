package com.start.spring.springboot_tutorial.error;

import com.start.spring.springboot_tutorial.entity.ErrorMessage;
import jakarta.validation.ValidationException;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import java.util.NoSuchElementException;

@ControllerAdvice
@Slf4j
public class FinalExceptionHandler {

    Logger LOGGER = LoggerFactory.getLogger(FinalExceptionHandler.class);

//works for MethodArgumentTypeMismatchException only
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ErrorMessage> methodArgumentNotValidException(MethodArgumentTypeMismatchException e) {
        LOGGER.error("MethodArgumentTypeMismatchException: " + e.getMessage());
        ErrorMessage message = new ErrorMessage(HttpStatus.BAD_REQUEST,
                "{MethodArgumentTypeMismatchException caught:" + e.getMessage() + "}");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(message);
    }

    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<ErrorMessage> NoSuchElementException(NoSuchElementException exception, WebRequest request) {
        LOGGER.warn("No such dept exists.");
        ErrorMessage message = new ErrorMessage(HttpStatus.NOT_FOUND, "Wrong dept name:" + exception.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(message);
    }


    @ExceptionHandler(DataAccessException.class)
    public ResponseEntity<ErrorMessage> dataAccessException(DataAccessException exception, WebRequest request) {
        LOGGER.warn("DataSource error: " + exception.getMessage());
        ErrorMessage message = new ErrorMessage(HttpStatus.INTERNAL_SERVER_ERROR, "Datasource error: " + exception.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(message);
    }


    //   Works for all not handled explicitly by dedicated handlers
    @ExceptionHandler(NoResourceFoundException.class)
    public ResponseEntity<ErrorMessage> noResourceFoundException(NoResourceFoundException e) {
        LOGGER.error("NoResourceFoundExceptioncaught:" + e.getMessage());
        ErrorMessage message = new ErrorMessage(HttpStatus.NOT_FOUND,
                "{Exception caught:" + e.getMessage() + "}");
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(message);
    }


    //   Works for all not handled explicitly by dedicated handlers
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorMessage> warnAllOtherException(Exception e) {
        LOGGER.error("Exception caught:" + e.getMessage());
        ErrorMessage message = new ErrorMessage(HttpStatus.BAD_REQUEST,
                "{Exception caught:" + e.getMessage() + "}");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(message);
    }

}
