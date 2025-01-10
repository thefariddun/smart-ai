package uz.smart_ai.smart_ai.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import uz.smart_ai.smart_ai.exceptions.BadException;

@ControllerAdvice
public class HandlerController {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> exception(BadException e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }

}
