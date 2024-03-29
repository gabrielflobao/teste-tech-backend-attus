package com.br.teste.attus.mapper;

import com.br.teste.attus.dto.EnderecoDTO;
import com.br.teste.attus.dto.PessoaDTO;
import com.br.teste.attus.entity.Endereco;
import com.br.teste.attus.entity.Pessoa;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Author : Gabriel F F Lobão
 */
public class PessoaMapper {

    public static PessoaDTO toReponse(Pessoa object) {
        if (object != null) {
            PessoaDTO dto = new PessoaDTO();
            dto.setNomeCompleto(object.getNomeCompleto());
            dto.setDataNascimento(object.getDataNascimento());
            dto.setEnderecos(EnderecoMapper.toReponseList(object.getEnderecos()));
            return dto;
        }
        return new PessoaDTO();
    }

    public static List<PessoaDTO> toReponseList(List<Pessoa> list) {
        return list.stream().map(PessoaMapper::toReponse).collect(Collectors.toList());
    }

    public static Pessoa toRequest(PessoaDTO object) {
        Pessoa entity = new Pessoa();
        entity.setNomeCompleto(object.getNomeCompleto());
        entity.setDataNascimento(object.getDataNascimento());
        entity.setEnderecos(EnderecoMapper.toRequestList(object.getEnderecos()));
        return entity;

    }
    public static List<Pessoa> toRequestList(List<PessoaDTO> list) {
        if(!list.isEmpty()) {
            return list.stream().map(PessoaMapper::toRequest).collect(Collectors.toList());
        }
        return new ArrayList<Pessoa>();

    }
}
