package ul.ulstu.tamada.rest.advice;

import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import ul.ulstu.tamada.rest.dto.ErrorResponse;

@Log4j2
@ControllerAdvice
public class TamadaExceptionHandler {

    @ExceptionHandler
    public ResponseEntity<ErrorResponse> internalServerError(Throwable exception) {
        log.error(String.format("internalServerError class: %s  message: %s", exception.getClass().getName(), exception.getMessage()));
        log.error(exception);
        var response = createErrorResponse(exception);
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private ErrorResponse createErrorResponse(Throwable exception) {
        var response = new ErrorResponse();
        response.setCode(exception.getClass().getSimpleName());
        response.setMessage(exception.getMessage());
        return response;
    }
}
