package com.br.teste.attus.exceptions.pessoa;

import com.br.teste.attus.dto.ErrorDTO;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

/**
 * Author : Gabriel F F Lobão
 */
@RestControllerAdvice
public class PessoaExceptionHandler {

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    @ExceptionHandler(PessoaNotFoundException.class)
    public ErrorDTO hander(PessoaNotFoundException ex) {
        return new ErrorDTO(ex.getMessage(),ex.getLancamento());

    }


}
