package com.br.teste.attus.exceptions.endereco;

import com.br.teste.attus.exceptions.AbstractException;

public class EnderecoPrincipalFoundException extends AbstractException {
    public EnderecoPrincipalFoundException(String message, String lancamento) {
        super(message, lancamento);
    }
}
