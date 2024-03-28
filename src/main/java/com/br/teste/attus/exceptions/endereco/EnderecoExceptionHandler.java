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
    @ExceptionHandler(EnderecoInexistenteException.class)
    public ErrorDTO hander(EnderecoInexistenteException ex) {
        return new ErrorDTO(ex.getMessage(),ex.getLancamento());

    }

    @ResponseStatus(HttpStatus.FOUND)
    @ResponseBody
    @ExceptionHandler(EnderecoPrincipalExisteException.class)
    public ErrorDTO hander(EnderecoPrincipalExisteException ex) {
        return new ErrorDTO(ex.getMessage(),ex.getLancamento());

    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    @ExceptionHandler(EnderecoPrincipalInexistente.class)
    public ErrorDTO hander(EnderecoPrincipalInexistente ex) {
        return new ErrorDTO(ex.getMessage(),ex.getLancamento());

    }
}
