package com.br.teste.attus.exceptions.endereco;

import com.br.teste.attus.exceptions.AbstractException;

public class EnderecoPrincipalExisteException extends AbstractException {
    public EnderecoPrincipalExisteException(String message, String lancamento) {
        super(message, lancamento);
    }
}
