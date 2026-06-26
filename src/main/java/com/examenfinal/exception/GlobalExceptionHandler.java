package com.examenfinal.exception;

import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(PreguntaNoEncontradaException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String handlePreguntaNoEncontrada(PreguntaNoEncontradaException ex, Model model) {
        model.addAttribute("error", ex.getMessage());
        return "error/404";
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public String handleGeneral(Exception ex, Model model) {
        model.addAttribute("error", "Ha ocurrido un error inesperado: " + ex.getMessage());
        return "error/500";
    }

}