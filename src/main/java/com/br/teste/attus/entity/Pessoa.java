package com.br.teste.attus.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.springframework.format.annotation.DateTimeFormat;

import java.sql.Date;
import java.util.List;
import java.util.Objects;
/**
 * Author : Gabriel F F Lobão
 */
@Entity
@Table(name = "TB_PESSOA")
public class Pessoa {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "NM_PESSOA")
    private String nomeCompleto;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    @JsonFormat(pattern = "dd/MM/yyyy")
    @Column(name = "DT_NASCI")
    private Date dataNascimento;
    public Pessoa() {

    }

    public Pessoa(Long id, String nomeCompleto, Date dataNascimento, List<Endereco> enderecos) {
        this.id = id;
        this.nomeCompleto = nomeCompleto;
        this.dataNascimento = dataNascimento;
        this.enderecos = enderecos;
    }

    public Pessoa(String nomeCompleto, Date dataNascimento, List<Endereco> enderecos) {
        this.nomeCompleto = nomeCompleto;
        this.dataNascimento = dataNascimento;
        this.enderecos = enderecos;
    }

    @OneToMany(mappedBy = "pessoa",fetch = FetchType.LAZY , cascade = {CascadeType.ALL})

    private List<Endereco> enderecos;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNomeCompleto() {
        return nomeCompleto;
    }

    public void setNomeCompleto(String nomeCompleto) {
        this.nomeCompleto = nomeCompleto;
    }

    public Date getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(Date dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public List<Endereco> getEnderecos() {
        return enderecos;
    }

    public void setEnderecos(List<Endereco> enderecos) {
        this.enderecos = enderecos;
    }

    @Override
    public boolean equals(Object obj) {
        return EqualsBuilder.reflectionEquals(obj,this);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nomeCompleto, dataNascimento, enderecos);
    }


}

