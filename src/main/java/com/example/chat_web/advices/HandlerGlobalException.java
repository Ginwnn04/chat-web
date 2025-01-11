package com.example.chat_web.advices;


import com.example.chat_web.exceptions.DataNotFoundException;
import com.example.chat_web.response.APIResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class HandlerGlobalException {
    @ExceptionHandler(DataNotFoundException.class)
    public ResponseEntity<APIResponse> handleDataNotFoundException(DataNotFoundException ex){
        APIResponse apiResponse = new APIResponse();
        apiResponse.setSuccess(false);
        apiResponse.setMessage(ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(apiResponse);
    }


}
