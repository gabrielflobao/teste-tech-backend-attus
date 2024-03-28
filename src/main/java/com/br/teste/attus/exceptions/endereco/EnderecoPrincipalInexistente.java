package com.br.teste.attus.exceptions.endereco;

import com.br.teste.attus.exceptions.AbstractException;

public class EnderecoPrincipalInexistente extends AbstractException {
    public EnderecoPrincipalInexistente(String message, String lancamento) {
        super(message, lancamento);
    }
}
