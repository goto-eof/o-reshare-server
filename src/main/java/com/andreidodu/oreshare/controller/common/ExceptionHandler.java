package com.andreidodu.oreshare.controller.common;

import com.andreidodu.oreshare.dto.ServerErrorResultDTO;
import com.andreidodu.oreshare.exception.ApplicationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;

@ControllerAdvice
public class ExceptionHandler {

    @org.springframework.web.bind.annotation.ExceptionHandler(ApplicationException.class)
    public ResponseEntity<ServerErrorResultDTO> handleApplicationException(ApplicationException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ServerErrorResultDTO(1, e.getMessage()));
    }
}
