package com.br.teste.attus.exceptions.pessoa;

import com.br.teste.attus.exceptions.AbstractException;

public class PessoaNotFoundException extends AbstractException {

    public PessoaNotFoundException(String message, String lancamento) {
        super(message, lancamento);
    }
}
