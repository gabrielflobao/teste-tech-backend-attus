package com.br.teste.attus.exceptions.endereco;

import com.br.teste.attus.exceptions.AbstractException;

public class EnderecoNotFoundException extends AbstractException {
    public EnderecoNotFoundException(String message, String lancamento) {
        super(message, lancamento);
    }
}
