package krefature.studvisit.web.controller.advice;

import jakarta.validation.ConstraintViolationException;
import krefature.studvisit.common.exceptions.NotFoundException;
import krefature.studvisit.common.exceptions.InvalidForeignKeyException;
import krefature.studvisit.common.exceptions.DependentEntityException;
import krefature.studvisit.web.dto.ErrorResponse;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidationExceptions(
            MethodArgumentNotValidException ex) {

        List<String> details = ex.getBindingResult().getFieldErrors().stream()
                .map(err -> err.getField() + ": " + err.getDefaultMessage())
                .collect(Collectors.toList());

        ErrorResponse error = new ErrorResponse(false, HttpStatus.BAD_REQUEST.value(), "Validation Failed", details);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ErrorResponse> handleConstraintViolation(
            ConstraintViolationException ex) {

        List<String> details = ex.getConstraintViolations().stream()
                .map(violation -> violation.getPropertyPath() + ": " + violation.getMessage())
                .collect(Collectors.toList());

        ErrorResponse error = new ErrorResponse(false, HttpStatus.BAD_REQUEST.value(), "Validation Failed", details);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ErrorResponse> handleNotFound(NotFoundException ex) {
        ErrorResponse error = new ErrorResponse(false, HttpStatus.NOT_FOUND.value(), ex.getMessage(), null);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }

    @ExceptionHandler(InvalidForeignKeyException.class)
    public ResponseEntity<ErrorResponse> handleInvalidForeignKey(InvalidForeignKeyException ex) {
        List<String> details = new ArrayList<>();
        if (ex.getFieldName() != null && ex.getFieldValue() != null) {
            details.add(ex.getFieldName() + "=" + ex.getFieldValue());
        }
        ErrorResponse error = new ErrorResponse(false, HttpStatus.BAD_REQUEST.value(), ex.getMessage(), details.isEmpty() ? null : details);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

    @ExceptionHandler(DependentEntityException.class)
    public ResponseEntity<ErrorResponse> handleDependentEntity(DependentEntityException ex) {
        List<String> details = new ArrayList<>();
        if (ex.getDependentEntities() != null && !ex.getDependentEntities().isEmpty()) {
            details.addAll(ex.getDependentEntities());
        }
        ErrorResponse error = new ErrorResponse(false, HttpStatus.CONFLICT.value(), ex.getMessage(), details.isEmpty() ? null : details);
        return ResponseEntity.status(HttpStatus.CONFLICT).body(error);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResponse> handleIllegalArgument(IllegalArgumentException ex) {
        ErrorResponse error = new ErrorResponse(false, HttpStatus.CONFLICT.value(), ex.getMessage(), null);
        return ResponseEntity.status(HttpStatus.CONFLICT).body(error);
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ErrorResponse> handleBadCredentials(BadCredentialsException ex) {
        ErrorResponse error = new ErrorResponse(false, HttpStatus.UNAUTHORIZED.value(), ex.getMessage(), null);
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(error);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ErrorResponse> handleAccessDenied(AccessDeniedException ex) {
        ErrorResponse error = new ErrorResponse(false, HttpStatus.FORBIDDEN.value(), ex.getMessage(), null);
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(error);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGeneric(Exception ex) {
        List<String> details = new ArrayList<>();
        if (ex.getMessage() != null) details.add(ex.getMessage());
        ErrorResponse error = new ErrorResponse(false, HttpStatus.INTERNAL_SERVER_ERROR.value(), "Internal Server Error", details);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
    }
}
