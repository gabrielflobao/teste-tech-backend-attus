package com.br.teste.attus.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.springframework.format.annotation.DateTimeFormat;

import java.sql.Date;
import java.util.List;

public class PessoaSaveDTO {
    private String nomeCompleto;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    @JsonFormat(pattern = "dd/MM/yyyy")
    private Date dataNascimento;

    private List<EnderecoSaveDTO> enderecos;

    public PessoaSaveDTO(String nomeCompleto, Date dataNascimento) {

        this.nomeCompleto = nomeCompleto;
        this.dataNascimento = dataNascimento;

    }

    public PessoaSaveDTO(String nomeCompleto, Date dataNascimento, List<EnderecoSaveDTO> enderecos) {
        this.nomeCompleto = nomeCompleto;
        this.dataNascimento = dataNascimento;
        this.enderecos = enderecos;

    }

    public PessoaSaveDTO() {
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

    public List<EnderecoSaveDTO> getEnderecos() {
        return enderecos;
    }

    public void setEnderecos(List<EnderecoSaveDTO> enderecos) {
        this.enderecos = enderecos;
    }

    @Override
    public boolean equals(Object obj) {
        return EqualsBuilder.reflectionEquals(obj,this);
    }
}
