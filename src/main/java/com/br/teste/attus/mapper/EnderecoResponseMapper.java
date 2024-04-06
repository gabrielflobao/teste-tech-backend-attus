package com.br.teste.attus.mapper;

import com.br.teste.attus.dto.EnderecoDTO;
import com.br.teste.attus.entity.Endereco;

import java.util.List;
import java.util.stream.Collectors;

public class EnderecoResponseMapper {
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
            return list.stream()
                    .map(EnderecoResponseMapper::toReponse)
                    .collect(Collectors.toList());
    }
}
