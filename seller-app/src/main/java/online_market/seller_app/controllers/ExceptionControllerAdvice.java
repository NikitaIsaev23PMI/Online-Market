package online_market.seller_app.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.server.ResponseStatusException;

import java.nio.file.AccessDeniedException;

import static org.springframework.http.HttpStatus.FORBIDDEN;

@ControllerAdvice
public class ExceptionControllerAdvice {

    @ExceptionHandler(AccessDeniedException.class)
    public String handleAccessDeniedException(AccessDeniedException exception, Model model){
        model.addAttribute("errors", exception.getMessage());
        return "errors/403";
    }

    @ExceptionHandler(ResponseStatusException.class)
    public String handleResponseStatusException(ResponseStatusException exception, Model model){
        HttpStatusCode statusCode = exception.getStatusCode();

        if(statusCode == HttpStatus.FORBIDDEN){
            model.addAttribute("errors", exception.getMessage());
            return "errors/403";
        }
        if (statusCode == HttpStatus.NOT_FOUND) {
            model.addAttribute("errors", exception.getMessage());
            return "errors/404";
        }
        if (statusCode == HttpStatus.BAD_REQUEST) {
            model.addAttribute("errors", exception.getMessage());
            return "errors/400";
        } else return "unknown_error";
    } //TODO реализовать предстваления на эти ошибки

}
