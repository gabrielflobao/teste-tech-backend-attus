package com.br.teste.attus.mapper;

import com.br.teste.attus.dto.PessoaDTO;
import com.br.teste.attus.dto.PessoaSaveDTO;
import com.br.teste.attus.entity.Pessoa;

import java.util.List;
import java.util.stream.Collectors;

public class PessoaSaveResponseMapper {
    public static PessoaSaveDTO toResponse(Pessoa object) {
        PessoaSaveDTO entity = new PessoaSaveDTO();
        entity.setNomeCompleto(object.getNomeCompleto());
        entity.setDataNascimento(object.getDataNascimento());
        return entity;

    }
    public static List<PessoaSaveDTO> toResponseList(List<Pessoa> object) {
       return object.stream().map( pessoa -> toResponse(pessoa)).collect(Collectors.toList());

    }

}
