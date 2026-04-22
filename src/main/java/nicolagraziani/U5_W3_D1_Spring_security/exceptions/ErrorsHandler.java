package nicolagraziani.U5_W3_D1_Spring_security.exceptions;

import nicolagraziani.U5_W3_D1_Spring_security.payloads.ErrorsDTO;
import nicolagraziani.U5_W3_D1_Spring_security.payloads.ErrorsListDTO;
import org.springframework.http.HttpStatus;
import org.springframework.security.authorization.AuthorizationDeniedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class ErrorsHandler {
    @ExceptionHandler(BadRequestException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST) // 400
    public ErrorsDTO handleBadRequest(BadRequestException ex) {
        return new ErrorsDTO(ex.getMessage(), LocalDateTime.now());
    }

    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND) // 404
    public ErrorsListDTO handleNotFoundEx(NotFoundException ex) {
        return new ErrorsListDTO(ex.getMessage(), LocalDateTime.now(), ex.getErrors());
    }

    @ExceptionHandler(ValidationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST) //400
    public ErrorsListDTO handleValidationEx(ValidationException ex) {
        return new ErrorsListDTO(ex.getMessage(), LocalDateTime.now(), ex.getErrors());
    }

    @ExceptionHandler(AuthorizationDeniedException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN) // 403
    public ErrorsDTO handleAuthorizationDeniedEx(AuthorizationDeniedException ex) {
        return new ErrorsDTO("Non hai i permessi per accedere", LocalDateTime.now());
    }
    
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR) // 500
    public ErrorsDTO handleGenericEx(Exception ex) {
        ex.printStackTrace();
        return new ErrorsDTO("Errore del server, stiamo lavorando per voi...", LocalDateTime.now());
    }

    @ExceptionHandler(UnauthorizedException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED) // 401
    public ErrorsDTO handleUnauthorizedEx(UnauthorizedException ex) {
        return new ErrorsDTO(ex.getMessage(), LocalDateTime.now());
    }
}
