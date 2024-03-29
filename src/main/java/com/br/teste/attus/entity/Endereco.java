package com.br.teste.attus.entity;

import com.br.teste.attus.enuns.EstadoBrasil;
import com.br.teste.attus.enuns.TipoPrincipal;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

/**
 * Author : Gabriel F F Lobão
 */
@Entity
@Table(name = "TB_ENDERECO")
public class Endereco {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(name = "LOGRADOURO")
    private String logradouro;

    @Column(name = "CEP")
    private String cep;

    @Column(name = "NUMERO")
    private Integer numero;

    @Column(name = "CIDADE")
    private String cidade;
    @Enumerated(EnumType.STRING)
    @Column(name = "TP_PRINCIPAL", columnDefinition = "CHAR DEFAULT 'N' ",nullable = false)
    private TipoPrincipal tpPrincipal;
    @Enumerated
    @Column(name = "ESTADO")
    private EstadoBrasil estado;

    @ManyToOne(fetch = FetchType.EAGER,targetEntity = Pessoa.class)
    @JsonIgnore
    private Pessoa pessoa;

    public Endereco() {

    }
    public Endereco(String logradouro, String cep, Integer numero, String cidade,  EstadoBrasil estado, TipoPrincipal tpPrincipal, Pessoa pessoa) {
        this.logradouro = logradouro;
        this.cep = cep;
        this.numero = numero;
        this.cidade = cidade;
        this.tpPrincipal = tpPrincipal;
        this.estado = estado;
        this.pessoa = pessoa;
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

    public void setPessoa(Pessoa pessoa) {
        this.pessoa = pessoa;
    }
    public Pessoa getPessoa() {
        return pessoa;
    }
}
