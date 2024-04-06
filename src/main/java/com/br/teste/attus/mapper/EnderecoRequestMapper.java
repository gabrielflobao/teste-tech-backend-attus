package com.br.teste.attus.mapper;

import com.br.teste.attus.dto.EnderecoDTO;
import com.br.teste.attus.entity.Endereco;

import java.util.List;
import java.util.stream.Collectors;

public class EnderecoRequestMapper {
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
        return list.stream().map(EnderecoRequestMapper::toRequest).collect(Collectors.toList());


    }

}
