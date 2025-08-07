package br.com.aguadelivery.product_service.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    // Captura erros de validação (ex: @NotNull, @NotBlank)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<Object> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error ->
                errors.put(error.getField(), error.getDefaultMessage()));
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }

    // Captura erros de violação de constraints do banco (ex: campo único)
    @ExceptionHandler(SQLIntegrityConstraintViolationException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ResponseEntity<Object> handleConstraintViolation(SQLIntegrityConstraintViolationException ex) {
        return new ResponseEntity<>(Map.of("error", "Já existe um registro com esses dados: " + ex.getMessage()), HttpStatus.CONFLICT);
    }

    // Captura a sua exceção customizada de negócio
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<Object> handleGenericRuntimeException(RuntimeException ex) {
        // Imprime o erro completo no console para depuração
        ex.printStackTrace();
        String errorMessage = "Ocorreu um erro de negócio: " + ex.getMessage();
        return new ResponseEntity<>(Map.of("error", errorMessage), HttpStatus.BAD_REQUEST);
    }


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