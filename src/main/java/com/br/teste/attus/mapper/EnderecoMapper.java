package com.br.teste.attus.mapper;

import com.br.teste.attus.dto.EnderecoDTO;
import com.br.teste.attus.dto.PessoaDTO;
import com.br.teste.attus.entity.Endereco;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Author : Gabriel F F Lobão
 */
public class EnderecoMapper {


    public static EnderecoDTO toReponse(Endereco object) {
        EnderecoDTO dto = new EnderecoDTO();
        dto.setId(object.getId());
        dto.setLogradouro(object.getLogradouro());
        dto.setCep(object.getCep());
        dto.setNumero(object.getNumero());
        dto.setCidade(object.getCidade());
        dto.setEstado(object.getEstado());
        return dto;
    }

    public static List<EnderecoDTO> toReponseList(List<Endereco> list) {
        return list.stream()
                .map(EnderecoMapper::toReponse)
                .collect(Collectors.toList());
    }

    public static Endereco toRequest(EnderecoDTO object) {
        Endereco entity = new Endereco();
        entity.setLogradouro(object.getLogradouro());
        entity.setCep(object.getCep());
        entity.setNumero(object.getNumero());
        entity.setCidade(object.getCidade());
        entity.setEstado(object.getEstado());
        entity.setTpPrincipal(object.getTpPrincipal());
        return entity;
    }

    public static List<Endereco> toRequestList(List<EnderecoDTO> list) {
        return list.stream().map(EnderecoMapper::toRequest).collect(Collectors.toList());

    }

}
