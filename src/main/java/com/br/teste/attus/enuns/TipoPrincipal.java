package com.br.teste.attus.enuns;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

/**
 * Author : Gabriel F F Lobão
 */

public enum TipoPrincipal {

    S("S"),N("N");

    @Id
    private Long id;
    TipoPrincipal(String descricao) {
        this.descricao = descricao;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    private String descricao;


}
