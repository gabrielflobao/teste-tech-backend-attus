package com.br.teste.attus.exceptions.pessoa;

import com.br.teste.attus.exceptions.AbstractException;

/**
 * Author : Gabriel F F Lobão
 */
public class PessoaInexistenteException extends AbstractException {
    public PessoaInexistenteException(String message, String lancamento) {
        super(message, lancamento);

    }
}
