package com.br.teste.attus.exceptions;

public class AbstractException extends RuntimeException{

    private final String lancamento;

    public AbstractException(String message, String lancamento) {
        super(message);
        this.lancamento = lancamento;
    }

    public String getLancamento() {
        return lancamento;
    }
}
