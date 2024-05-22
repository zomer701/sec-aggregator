package io.scommerce.core.inbound.shared.advice;

import io.scommerce.core.inbound.shared.exception.BadRequestException;
import io.scommerce.core.inbound.shared.exception.InvalidInputException;
import io.scommerce.core.inbound.shared.exception.NotFoundException;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.bind.support.WebExchangeBindException;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.HttpStatus.UNPROCESSABLE_ENTITY;

@RestControllerAdvice
@Slf4j
public class GlobalControllerExceptionHandler {

    @ResponseStatus(BAD_REQUEST)
    @ExceptionHandler(BadRequestException.class)
    public @ResponseBody HttpErrorInfo handleBadRequestExceptions(
            ServerHttpRequest request, BadRequestException ex) {

        return createHttpErrorInfo(BAD_REQUEST, request, ex);
    }

    @ResponseStatus(NOT_FOUND)
    @ExceptionHandler(NotFoundException.class)
    public @ResponseBody HttpErrorInfo handleNotFoundExceptions(
            ServerHttpRequest request, NotFoundException ex) {

        return createHttpErrorInfo(NOT_FOUND, request, ex);
    }

    @ResponseStatus(UNPROCESSABLE_ENTITY)
    @ExceptionHandler(InvalidInputException.class)
    public @ResponseBody HttpErrorInfo handleInvalidInputException(
            ServerHttpRequest request, InvalidInputException ex) {
        return createHttpErrorInfo(UNPROCESSABLE_ENTITY, request, ex);
    }

    private HttpErrorInfo createHttpErrorInfo(
            HttpStatus httpStatus, ServerHttpRequest request, Exception ex) {

        final String path = request.getPath().pathWithinApplication().value();
        final String message = ex.getMessage();

        log.info("Returning HTTP status: {} for path: {}, message: {}", httpStatus, path, message);
        return new HttpErrorInfo(httpStatus, path, message);
    }

    @ExceptionHandler(WebExchangeBindException.class)
    public ResponseEntity<?> handleException(WebExchangeBindException e) {
        log.info("WebExchangeBindException", e);
        return ResponseEntity.badRequest()
                .body(Void.class);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<?> handleException(ConstraintViolationException e) {
        log.info("ConstraintViolationException", e);
        return ResponseEntity.badRequest()
                .body(Void.class);
    }

}