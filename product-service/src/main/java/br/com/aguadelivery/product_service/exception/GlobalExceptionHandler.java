package br.com.aguadelivery.product_service.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {


    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleGenericException(Exception ex) {
        // Imprime o erro completo no console do Docker para podermos ver a causa
        System.err.println("Uma exceção foi capturada pelo GlobalExceptionHandler:");
        ex.printStackTrace();

        // Retorna uma mensagem de erro clara para o Postman
        String errorMessage = "Ocorreu um erro inesperado: " + ex.getMessage();
        return new ResponseEntity<>(Map.of("error", errorMessage), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
