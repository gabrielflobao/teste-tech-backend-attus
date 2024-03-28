package com.br.teste.attus.exceptions;

public class PessoaExistenteException extends AbstractException{
    public PessoaExistenteException(String message, String lancamento) {
        super(message, lancamento);

    }
}
