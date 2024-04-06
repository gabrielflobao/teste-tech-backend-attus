package com.br.teste.attus.mapper;

import com.br.teste.attus.dto.PessoaDTO;
import com.br.teste.attus.entity.Pessoa;

import java.util.List;
import java.util.stream.Collectors;

public class PessoaResponseMapper {
    public static PessoaDTO toReponse(Pessoa object) {
            PessoaDTO dto = new PessoaDTO();
            dto.setId(object.getId());
            dto.setNomeCompleto(object.getNomeCompleto());
            dto.setDataNascimento(object.getDataNascimento());
            dto.setEnderecos(EnderecoResponseMapper.toReponseList(object.getEnderecos()));
            return dto;
    }

    public static List<PessoaDTO> toReponseList(List<Pessoa> list) {
        return list.stream().map(PessoaResponseMapper::toReponse).collect(Collectors.toList());
    }
}
