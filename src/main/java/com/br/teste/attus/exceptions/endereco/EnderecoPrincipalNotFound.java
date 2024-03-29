package com.br.teste.attus.exceptions.endereco;

import com.br.teste.attus.exceptions.AbstractException;

public class EnderecoPrincipalNotFound extends AbstractException {
    public EnderecoPrincipalNotFound(String message, String lancamento) {
        super(message, lancamento);
    }
}
