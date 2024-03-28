package com.br.teste.attus.exceptions.endereco;

import com.br.teste.attus.exceptions.AbstractException;

public class EnderecoInexistenteException extends AbstractException {
    public EnderecoInexistenteException(String message, String lancamento) {
        super(message, lancamento);
    }
}
