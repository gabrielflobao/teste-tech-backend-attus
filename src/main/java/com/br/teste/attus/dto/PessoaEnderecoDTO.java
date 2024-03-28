package com.br.teste.attus.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.sql.Date;
import java.util.List;

public class PessoaEnderecoDTO {

    private Long id;

    private String nomeCompleto;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    private Date dataNascimento;

    private List<EnderecoDTO> enderecos;

    public PessoaEnderecoDTO(Long id, String nomeCompleto, Date dataNascimento, List<EnderecoDTO> enderecos) {
        this.id = id;
        this.nomeCompleto = nomeCompleto;
        this.dataNascimento = dataNascimento;
        this.enderecos = enderecos;
    }

    public PessoaEnderecoDTO() {

    }

    public Long getId() {
        return id;
    }

    public String getNomeCompleto() {
        return nomeCompleto;
    }

    public Date getDataNascimento() {
        return dataNascimento;
    }

    public List<EnderecoDTO> getEnderecos() {
        return enderecos;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setNomeCompleto(String nomeCompleto) {
        this.nomeCompleto = nomeCompleto;
    }

    public void setDataNascimento(Date dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public void setEnderecos(List<EnderecoDTO> enderecos) {
        this.enderecos = enderecos;
    }
}
