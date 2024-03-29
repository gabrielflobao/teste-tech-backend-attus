package com.br.teste.attus.exceptions.endereco;

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

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    @ExceptionHandler(EnderecoNotFoundException.class)
    public ErrorDTO hander(EnderecoNotFoundException ex) {
        return new ErrorDTO(ex.getMessage(),ex.getLancamento());

    }

    @ResponseStatus(HttpStatus.FOUND)
    @ResponseBody
    @ExceptionHandler(EnderecoPrincipalFoundException.class)
    public ErrorDTO hander(EnderecoPrincipalFoundException ex) {
        return new ErrorDTO(ex.getMessage(),ex.getLancamento());

    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    @ExceptionHandler(EnderecoPrincipalNotFound.class)
    public ErrorDTO hander(EnderecoPrincipalNotFound ex) {
        return new ErrorDTO(ex.getMessage(),ex.getLancamento());

    }
}
