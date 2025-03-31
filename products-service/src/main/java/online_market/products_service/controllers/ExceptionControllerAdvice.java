package online_market.products_service.controllers;

import org.apache.coyote.BadRequestException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.NoSuchElementException;


@ControllerAdvice
public class ExceptionControllerAdvice {

    @ExceptionHandler(BindException.class)
    public ResponseEntity<ProblemDetail> handleBindException(BindException exception) {
        ProblemDetail problemDetail = ProblemDetail
                .forStatusAndDetail(HttpStatus.BAD_REQUEST, "Ошибка в создании или редактировании товара");
        problemDetail.setProperty("errors", exception.getAllErrors()
                        .stream()
                .map(ObjectError::getDefaultMessage)
                .toList());
        return ResponseEntity.badRequest().body(problemDetail);
    }

    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<ProblemDetail> handleNoSuchElementException(NoSuchElementException exception) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND,
                        exception.getMessage()));
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ProblemDetail> handleBadRequest(BadRequestException ex) {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST,
                "у товара может быть не более 6 фото или видео");
        problemDetail.setProperty("errors", ex.getMessage());

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(problemDetail);
    }

}
