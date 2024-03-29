package com.br.teste.attus.dto;

import com.br.teste.attus.entity.Pessoa;
import com.br.teste.attus.enuns.EstadoBrasil;
import com.br.teste.attus.enuns.TipoPrincipal;
import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * Author : Gabriel F F Lobão
 */
public class EnderecoDTO {

    private String logradouro;

    private String cep;

    private Integer numero;

    private String cidade;

    private TipoPrincipal tpPrincipal;
    private EstadoBrasil estado;
    @JsonIgnore
    private Pessoa pessoa;
    public EnderecoDTO () {

    }

    public EnderecoDTO(
                       String logradouro,
                       String cep, Integer numero
            , String cidade,
                       TipoPrincipal tpPrincipal, EstadoBrasil estado,Pessoa pessoa) {
        this.logradouro = logradouro;
        this.cep = cep;
        this.numero = numero;
        this.cidade = cidade;
        this.tpPrincipal = tpPrincipal;
        this.estado = estado;
        this.pessoa=pessoa;
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

    public Pessoa getPessoa() {
        return pessoa;
    }

    public void setPessoa(Pessoa pessoa) {
        this.pessoa = pessoa;
    }
}
