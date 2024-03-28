package com.br.teste.attus.exceptions.pessoa;

import com.br.teste.attus.exceptions.AbstractException;

public class PessoaExistenteException extends AbstractException {
    public PessoaExistenteException(String message, String lancamento) {
        super(message, lancamento);

    }
}
