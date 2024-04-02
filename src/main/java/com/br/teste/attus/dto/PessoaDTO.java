package com.br.teste.attus.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.sql.Date;
import java.util.List;
/**
 * Author : Gabriel F F Lobão
 */
public class PessoaDTO {

    private Long id;
    private String nomeCompleto;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    @JsonFormat(pattern = "dd/MM/yyyy")
    private Date dataNascimento;

    private List<EnderecoDTO> enderecos;

    public PessoaDTO(Long id, String nomeCompleto, Date dataNascimento) {

        this.nomeCompleto = nomeCompleto;
        this.dataNascimento = dataNascimento;

    }

    public PessoaDTO(String nomeCompleto, Date dataNascimento, List<EnderecoDTO> enderecos) {
        this.nomeCompleto = nomeCompleto;
        this.dataNascimento = dataNascimento;
        this.enderecos = enderecos;

    }

    public PessoaDTO() {
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

    public List<EnderecoDTO> getEnderecos() {
        return enderecos;
    }

    public void setEnderecos(List<EnderecoDTO> enderecos) {
        this.enderecos = enderecos;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    @Override
    public boolean equals(Object obj) {
        return EqualsBuilder.reflectionEquals(obj,this);
    }


}


