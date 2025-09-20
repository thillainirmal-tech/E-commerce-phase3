package com.example.minor_project_01.config;

import com.example.minor_project_01.dto.ResponseDTO;
import com.example.minor_project_01.exception.NotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice(annotations = RestController.class)
public class GlobalExceptionHandler {

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ResponseDTO> handleNotFoundException(NotFoundException exception){
        ResponseDTO responseDTO = new ResponseDTO();
        responseDTO.setMsg(exception.getMessage());
        responseDTO.setStatusCode("989");
        return ResponseEntity.badRequest().body(responseDTO);
    }
}
