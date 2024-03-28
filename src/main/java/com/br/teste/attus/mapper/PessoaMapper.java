package com.br.teste.attus.mapper;

import com.br.teste.attus.dto.PessoaDTO;
import com.br.teste.attus.dto.PessoaEnderecoDTO;
import com.br.teste.attus.entity.Pessoa;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Author : Gabriel F F Lobão
 */
public class PessoaMapper {

    public static PessoaDTO toReponse(Pessoa object) {
        if (object != null) {
            PessoaDTO dto = new PessoaDTO();
            dto.setId(object.getId());
            dto.setNomeCompleto(object.getNomeCompleto());
            dto.setDataNascimento(object.getDataNascimento());
            dto.setEnderecos(EnderecoMapper.toReponseList(object.getEnderecos()));
            return dto;
        }
       return new PessoaDTO();

    }

  public static PessoaDTO toReponse(PessoaEnderecoDTO object) {
        if (object != null) {
            PessoaDTO dto = new PessoaDTO();
            dto.setId(object.getId());
            dto.setNomeCompleto(object.getNomeCompleto());
            dto.setDataNascimento(object.getDataNascimento());
            dto.setEnderecos((object.getEnderecos()));
            return dto;
        }
        return new PessoaDTO();

    }



    public static List<PessoaDTO> toReponseList(List<Pessoa> list) {
        return list.stream().map(PessoaMapper::toReponse).collect(Collectors.toList());
    }

    public static Pessoa toRequest(PessoaDTO object) {
        Pessoa entity = new Pessoa();
        entity.setId(object.getId());
        entity.setNomeCompleto(object.getNomeCompleto());
        entity.setDataNascimento(object.getDataNascimento());
        entity.setEnderecos(EnderecoMapper.toRequestList(object.getEnderecos()));
        return entity;

    }

    public static Pessoa toRequest(PessoaEnderecoDTO object) {
        Pessoa entity = new Pessoa();
        entity.setId(object.getId());
        entity.setNomeCompleto(object.getNomeCompleto());
        entity.setDataNascimento(object.getDataNascimento());
        entity.setEnderecos(EnderecoMapper.toRequestList(object.getEnderecos()));

        return entity;

    }

}
