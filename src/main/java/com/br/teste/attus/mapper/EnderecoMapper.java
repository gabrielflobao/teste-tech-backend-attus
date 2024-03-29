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
public class EnderecoMapper {


    public static EnderecoDTO toReponse(Endereco object) {
        EnderecoDTO dto = new EnderecoDTO();
        dto.setLogradouro(object.getLogradouro());
        dto.setCep(object.getCep());
        dto.setNumero(object.getNumero());
        dto.setCidade(object.getCidade());
        dto.setTpPrincipal(object.getTpPrincipal());
        dto.setEstado(object.getEstado());
        dto.setId(object.getId());
        return dto;
    }

    public static List<EnderecoDTO> toReponseList(List<Endereco> list) {
        if (list != null) {
            return list.stream()
                    .map(EnderecoMapper::toReponse)
                    .collect(Collectors.toList());
        }
        return null;
    }

    public static Endereco toRequest(EnderecoDTO object) {
        Endereco entity = new Endereco();
        entity.setLogradouro(object.getLogradouro());
        entity.setCep(object.getCep());
        entity.setNumero(object.getNumero());
        entity.setCidade(object.getCidade());
        entity.setEstado(object.getEstado());
        entity.setTpPrincipal(object.getTpPrincipal());
        entity.setPessoa(object.getPessoa());
        return entity;
    }

    public static List<Endereco> toRequestList(List<EnderecoDTO> list) {
        return list.stream().map(EnderecoMapper::toRequest).collect(Collectors.toList());


    }

    public static List<Endereco> toRequestListSave(List<EnderecoDTO> enderecos, Pessoa pessoa) {
        if (enderecos != null) {
            List<Endereco> entities = new ArrayList<>();
            for (EnderecoDTO enderecoDTO : enderecos) {
                Endereco entity = new Endereco();
                entity.setLogradouro(enderecoDTO.getLogradouro());
                entity.setCep(enderecoDTO.getCep());
                entity.setNumero(enderecoDTO.getNumero());
                entity.setCidade(enderecoDTO.getCidade());
                entity.setEstado(enderecoDTO.getEstado());
                entity.setTpPrincipal(enderecoDTO.getTpPrincipal());
                entity.setPessoa(pessoa);
                entities.add(entity);
            }
            return entities;
        }
        return null;
    }
}
