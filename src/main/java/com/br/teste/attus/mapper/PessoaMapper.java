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
            dto.setId(object.getId());
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
        entity.setId(object.getId());
        entity.setNomeCompleto(object.getNomeCompleto());
        entity.setDataNascimento(object.getDataNascimento());
        if (object.getEnderecos() != null) {
            entity.setEnderecos(EnderecoMapper.toRequestListSave(object.getEnderecos(), entity));
        }
        return entity;

    }

    public static Pessoa toRequestSave(PessoaDTO object) {
        Pessoa entity = new Pessoa();
        entity.setId(object.getId());
        entity.setNomeCompleto(object.getNomeCompleto());
        entity.setDataNascimento(object.getDataNascimento());
        entity.setEnderecos(EnderecoMapper.toRequestListSave(object.getEnderecos(), entity));
        return entity;

    }

    public static List<Pessoa> toRequestList(List<PessoaDTO> list) {
        if (!list.isEmpty()) {
            return list.stream().map(PessoaMapper::toRequest).collect(Collectors.toList());
        }
        return new ArrayList<Pessoa>();

    }

    public static List<Pessoa> toRequestListSave(List<PessoaDTO> pessoa) {
        return pessoa.stream().map(PessoaMapper::toRequest).collect(Collectors.toList());
    }
}
