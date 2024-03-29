package com.br.teste.attus.dto;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDate;
import java.sql.Date;
import java.util.List;
/**
 * Author : Gabriel F F Lobão
 */
public class PessoaDTO {

    private String nomeCompleto;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    private Date dataNascimento;

    private List<EnderecoDTO> enderecos;

    public PessoaDTO(String nomeCompleto, Date dataNascimento) {

        this.nomeCompleto = nomeCompleto;
        this.dataNascimento = dataNascimento;

    }
    public PessoaDTO(String nomeCompleto, Date dataNascimento,List<EnderecoDTO> enderecos) {
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
}
