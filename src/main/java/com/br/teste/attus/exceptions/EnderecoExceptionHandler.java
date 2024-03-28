package com.br.teste.attus.exceptions;

import com.br.teste.attus.dto.ErrorDTO;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class EnderecoExceptionHandler {

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    @ExceptionHandler(EnderecoExistenteException.class)
    public ErrorDTO hander(EnderecoExistenteException ex) {
        return new ErrorDTO(ex.getMessage(),ex.getLancamento());

    }
}
