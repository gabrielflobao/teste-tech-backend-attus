package com.br.teste.attus.exceptions.endereco;

import com.br.teste.attus.exceptions.AbstractException;

public class EnderecoExistenteException extends AbstractException {
    public EnderecoExistenteException(String message, String lancamento) {
        super(message, lancamento);

    }
}
