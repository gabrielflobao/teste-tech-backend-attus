package com.br.teste.attus.dto;

import com.br.teste.attus.enuns.EstadoBrasil;
import com.br.teste.attus.enuns.TipoPrincipal;
import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * Author : Gabriel F F Lobão
 */
public class EnderecoDTO {

    private Long id;
    private String logradouro;

    private String cep;

    private Integer numero;

    private String cidade;

    private TipoPrincipal tpPrincipal;
    private EstadoBrasil estado;
    private Long id_pessoa;
    public EnderecoDTO () {

    }

    public EnderecoDTO(Long id,
                       String logradouro,
                       String cep, Integer numero
            , String cidade,
                       TipoPrincipal tpPrincipal, EstadoBrasil estado,Long id_pessoa) {
        this.id = id;
        this.logradouro = logradouro;
        this.cep = cep;
        this.numero = numero;
        this.cidade = cidade;
        this.tpPrincipal = tpPrincipal;
        this.estado = estado;
        this.id_pessoa = id_pessoa;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLogradouro() {
        return logradouro;
    }

    public void setLogradouro(String logradouro) {
        this.logradouro = logradouro;
    }

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public Integer getNumero() {
        return numero;
    }

    public void setNumero(Integer numero) {
        this.numero = numero;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public TipoPrincipal getTpPrincipal() {
        return tpPrincipal;
    }

    public void setTpPrincipal(TipoPrincipal tpPrincipal) {
        this.tpPrincipal = tpPrincipal;
    }

    public EstadoBrasil getEstado() {
        return estado;
    }

    public void setEstado(EstadoBrasil estado) {
        this.estado = estado;
    }

    public Long getId_pessoa() {
        return id_pessoa;
    }

    public void setId_pessoa(Long id_pessoa) {
        this.id_pessoa = id_pessoa;
    }
}
