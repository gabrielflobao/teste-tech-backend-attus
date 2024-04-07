package com.br.teste.attus.mapper;

import com.br.teste.attus.dto.PessoaDTO;
import com.br.teste.attus.entity.Pessoa;

import java.util.List;
import java.util.stream.Collectors;

public class PessoaRequestMapper {
    public static Pessoa toRequest(PessoaDTO object) {
        Pessoa entity = new Pessoa();
        entity.setNomeCompleto(object.getNomeCompleto());
        entity.setDataNascimento(object.getDataNascimento());
        if (object.getEnderecos() != null) {
            entity.setEnderecos(EnderecoRequestMapper.toRequestList(object.getEnderecos()));
        }
        return entity;
    }

    public static List<Pessoa> toRequestList(List<PessoaDTO> object) {
        return object.stream().map(PessoaRequestMapper::toRequest).collect(Collectors.toList());

    }
}
