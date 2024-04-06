package com.br.teste.attus.mapper;

import com.br.teste.attus.dto.PessoaDTO;
import com.br.teste.attus.entity.Pessoa;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class PessoaRequestMapper {
    public static Pessoa toRequest(PessoaDTO object) {
        Pessoa entity = new Pessoa();
        entity.setId(object.getId());
        entity.setNomeCompleto(object.getNomeCompleto());
        entity.setDataNascimento(object.getDataNascimento());
        return entity;

    }
    public static List<Pessoa> toRequestList(List<PessoaDTO> list) {
         return list.stream().map(PessoaRequestMapper::toRequest).collect(Collectors.toList());

    }
}
