package com.br.teste.attus.exceptions;
/**
 * Author : Gabriel F F Lobão
 */
public class PessoaInexistenteException extends AbstractException{
    public PessoaInexistenteException(String message, String lancamento) {
        super(message, lancamento);

    }
}
