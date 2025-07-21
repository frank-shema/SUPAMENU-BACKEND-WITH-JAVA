package com.commerce.supamenu.handlers;

import com.commerce.supamenu.dto.responses.ErrorResponse;
import com.commerce.supamenu.exceptions.*;

import java.util.HashMap;
import java.util.Map;

import jakarta.validation.ConstraintViolationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authorization.AuthorizationDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.multipart.support.MissingServletRequestPartException;
import org.springframework.web.servlet.resource.NoResourceFoundException;



@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    // Handle validation constraint violations (like @NotBlank, @Size, etc.)
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ErrorResponse> handleConstraintViolation(ConstraintViolationException ex) {
        Map<String, String> errors = new HashMap<>();

        ex.getConstraintViolations().forEach(violation -> {
            String propertyPath = violation.getPropertyPath().toString();
            String message = violation.getMessage();
            errors.put(propertyPath, message);
        });

        ErrorResponse errorResponse = new ErrorResponse(
                "VALIDATION_ERROR",
                "Validation failed for provided data.",
                errors);

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleUsernameNotFoundException(UsernameNotFoundException e){
        ErrorResponse response = new ErrorResponse(
                "INVALID_TOKEN",
                "Invalid token provided, login again to continue.",
                null
        );
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
    }


    @ExceptionHandler(UnsupportedOperationException.class)
    public ResponseEntity<ErrorResponse> handleUnsupportedOperation(UnsupportedOperationException ex) {
        System.out.println(ex);
        logger.error("UnsupportedOperationException: {}", ex.getMessage());
        
        ErrorResponse errorResponse = new ErrorResponse(
                "UNSUPPORTED_OPERATION",
                "Cannot modify an immutable collection. This typically occurs when trying to update a read-only relationship.",
                null);

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ErrorResponse> handleDataIntegrityViolation(DataIntegrityViolationException ex) {
        logger.error("DataIntegrityViolationException: {}", ex.getMessage());
        
        ErrorResponse errorResponse = new ErrorResponse(
                "CONSTRAINT_VIOLATION",
                "You can't delete a referenced record. This record is being used by other parts of the system.",
                null);

        return ResponseEntity.status(HttpStatus.CONFLICT).body(errorResponse);
    }

    // Handle MethodArgumentNotValidException (for annotated DTOs)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();

        ex.getBindingResult().getFieldErrors()
                .forEach(error -> errors.put(error.getField(), error.getDefaultMessage()));
        System.out.println(errors);

        ErrorResponse errorResponse = new ErrorResponse(
                "VALIDATION_ERROR",
                "Validation failed. Please check the errors and try again.",
                errors);

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

    // handle InvalidEnumConstantException
    @ExceptionHandler(InvalidEnumConstantException.class)
    ResponseEntity<ErrorResponse> handleInvalidEnumConstantException(InvalidEnumConstantException e){
        logger.error("InvalidEnumConstantException: {}", e);
        ErrorResponse response = new ErrorResponse("INVALID_ENUM_CONSTANT", e.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    // Handle AuthorizationDeniedException
    @ExceptionHandler(AuthorizationDeniedException.class)
    ResponseEntity<ErrorResponse> handleAuthorizationDeniedException(AuthorizationDeniedException e){
        logger.error("AuthorizationDeniedException: {}", e);
        ErrorResponse response = new ErrorResponse("ACCESS_DENIED", e.getMessage());
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
    }

    // Handle HttpMediaTypeNotSupportedException
    @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
    public ResponseEntity<ErrorResponse> handleHttpMediaTypeNotSupportedException(HttpMediaTypeNotSupportedException e){
        System.out.println(e);
        logger.error("HttpMediaTypeNotSupportedException: {}", e);
        ErrorResponse errorResponse = new ErrorResponse("UNSUPPORTED_MEDIA_TYPE", e.getMessage());
        return ResponseEntity.status(HttpStatus.UNSUPPORTED_MEDIA_TYPE).body(errorResponse);
    }

     @ExceptionHandler(FileStorageException.class)
    public ResponseEntity<ErrorResponse> handleFileStorageException(FileStorageException ex) {
        ErrorResponse errorResponse = new ErrorResponse("FILE_UPLOAD_ERROR", ex.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);   
    }

    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public ResponseEntity<ErrorResponse> handleMaxSizeException(MaxUploadSizeExceededException ex) {
        ErrorResponse response = new ErrorResponse("FILE_SIZE_EXCEEDED", "File size exceeds maximum allowed", null);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    // Handle UserNotFoundException
    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleUserNotFound(UserNotFoundException e) {
        logger.error("UserNotFoundException: {}", e);
        ErrorResponse errorResponse = new ErrorResponse("USER_NOT_FOUND", e.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
    }

    @ExceptionHandler(NoResourceFoundException.class)
    public ResponseEntity<ErrorResponse> handleNoResourceFoundException(NoResourceFoundException e){
        logger.error("NoResourceFoundException: {}", e);
        ErrorResponse errorResponse = new ErrorResponse("NO_RESOURCE_FOUND", e.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
    }

    // Handle NotFoundException
    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ErrorResponse> handleNotFound(NotFoundException e) {
        logger.error("NotFoundException: {}", e);
        ErrorResponse errorResponse = new ErrorResponse("NOT_FOUND", e.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
    }

    // Handle ForbiddenException
    @ExceptionHandler(ForbiddenException.class)
    public ResponseEntity<ErrorResponse> handleForbiddenException(ForbiddenException e) {
        logger.error("ForbiddenException: {}", e);
        ErrorResponse errorResponse = new ErrorResponse("FORBIDDEN", e.getMessage());
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(errorResponse);
    }

    // Handle UnauthorisedException
    @ExceptionHandler(UnauthorisedException.class)
    public ResponseEntity<ErrorResponse> handleUnauthorised(UnauthorisedException e) {
        logger.error("UnauthorisedException: {}", e);
        ErrorResponse errorResponse = new ErrorResponse("UNAUTHORIZED", e.getMessage());
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorResponse);
    }

    // Handle BadRequestException
    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ErrorResponse> handleBadRequest(BadRequestException e) {
        logger.error("BadRequestException: {}", e);
        ErrorResponse errorResponse = new ErrorResponse("BAD_REQUEST", e.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

    // Handle RunTimeException
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ErrorResponse> handleRunTimeException(RuntimeException e) {
        System.out.println(e);
        logger.error("RuntimeException: {}", e);
        ErrorResponse errorResponse = new ErrorResponse("RUN_TIME", e.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

    @ExceptionHandler(org.springframework.security.authentication.BadCredentialsException.class)
    public ResponseEntity<ErrorResponse> handleBadCredentials(
            org.springframework.security.authentication.BadCredentialsException e) {
        ErrorResponse errorResponse = new ErrorResponse("UNAUTHORIZED", "Invalid phone number or password");
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorResponse);
    }

    // Handle AuthenticationException (general authentication failure)
    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<ErrorResponse> handleAuthenticationException(AuthenticationException e) {
        ErrorResponse errorResponse = new ErrorResponse("UNAUTHORIZED", "Authentication failed");
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorResponse);
    }

    // Handle IllegalArgumentExceptionCustom
    @ExceptionHandler(IllegalArgumentExceptionCustom.class)
    public ResponseEntity<ErrorResponse> handleIllegalArgument(IllegalArgumentExceptionCustom e) {
        logger.error("IllegalArgumentExceptionCustom: {}", e.getMessage());
        ErrorResponse errorResponse = new ErrorResponse("ILLEGAL_ARGUMENT", e.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

    // Handle InternalServerErrorException
    @ExceptionHandler(InternalServerErrorException.class)
    public ResponseEntity<ErrorResponse> handleInternalServerError(InternalServerErrorException e) {
        logger.error("InternalServerErrorException: {}", e.getMessage());
        ErrorResponse errorResponse = new ErrorResponse("INTERNAL_SERVER_ERROR", e.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
    }

     @ExceptionHandler(MissingCertificateException.class)
    public ResponseEntity<ErrorResponse> handleMissingCertificate(MissingCertificateException ex) {
        ErrorResponse errorResponse = new ErrorResponse("BAD_REQUEST", ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

    @ExceptionHandler(MissingServletRequestPartException.class)
    public ResponseEntity<ErrorResponse> handleMissingServletRequestPart(
            MissingServletRequestPartException ex) {
        
                ErrorResponse errorResponse = new ErrorResponse("BAD_REQUEST", ex.getMessage());
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

    // Handle InvalidUUIDException
    @ExceptionHandler(InvalidUUIDException.class)
    public ResponseEntity<ErrorResponse> handleInvalidUUID(InvalidUUIDException e) {
        logger.error("InvalidUUIDException: {}", e.getMessage());
        ErrorResponse errorResponse = new ErrorResponse("INVALID_UUID", e.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

    // Handle NotificationException
    @ExceptionHandler(NotificationException.class)
    public ResponseEntity<ErrorResponse> handleNotificationException(NotificationException e) {
        logger.error("NotificationException: {}", e.getMessage());
        ErrorResponse errorResponse = new ErrorResponse("NOTIFICATION_ERROR", e.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
    }

    // Handle ResourceNotFoundException
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleResourceNotFound(ResourceNotFoundException e) {
        logger.error("ResourceNotFoundException: {}", e.getMessage());
        ErrorResponse errorResponse = new ErrorResponse("RESOURCE_NOT_FOUND", e.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
    }

    // Handle TokenException
    @ExceptionHandler(TokenException.class)
    public ResponseEntity<ErrorResponse> handleTokenException(TokenException e) {
        logger.error("TokenException: {}", e.getMessage());
        ErrorResponse errorResponse = new ErrorResponse("TOKEN_ERROR", e.getMessage());
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorResponse);
    }

    // Generic Exception Handler
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGenericException(Exception e) {
        System.out.println(e);
        logger.error("Generic Exception: {}", e.getMessage());
        ErrorResponse errorResponse = new ErrorResponse("INTERNAL_SERVER_ERROR", "An unexpected error occurred.");
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
    }
}

