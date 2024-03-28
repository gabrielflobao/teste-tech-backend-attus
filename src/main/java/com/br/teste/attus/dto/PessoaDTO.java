package com.br.teste.attus.dto;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
/**
 * Author : Gabriel F F Lobão
 */
public class PessoaDTO {

    private Long id;

    private String nomeCompleto;

    private Date dataNascimento;

    private List<EnderecoDTO> enderecos;

    public PessoaDTO(Long id, String nomeCompleto, Date dataNascimento) {
        this.id = id;
        this.nomeCompleto = nomeCompleto;
        this.dataNascimento = dataNascimento;

    }

    public PessoaDTO(Long id, String nomeCompleto, Date dataNascimento,List<EnderecoDTO> enderecos) {
        this.id = id;
        this.nomeCompleto = nomeCompleto;
        this.dataNascimento = dataNascimento;
        this.enderecos = enderecos;

    }

    public PessoaDTO() {
    }

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

    public List<EnderecoDTO> getEnderecos() {
        return enderecos;
    }

    public void setEnderecos(List<EnderecoDTO> enderecos) {
        this.enderecos = enderecos;
    }
}
