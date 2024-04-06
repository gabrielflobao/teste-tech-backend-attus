package com.br.teste.attus.mapper;

import com.br.teste.attus.dto.EnderecoDTO;
import com.br.teste.attus.dto.EnderecoSaveDTO;
import com.br.teste.attus.entity.Endereco;

import java.util.List;
import java.util.stream.Collectors;

public class EnderecoSaveResponseMapper {
    public static List<EnderecoSaveDTO> toReponseList(List<Endereco> list) {
            return list.stream()
                    .map(EnderecoSaveResponseMapper::toResponse)
                    .collect(Collectors.toList());

    }

    public static EnderecoSaveDTO toResponse(Endereco object) {
        EnderecoSaveDTO entity = new EnderecoSaveDTO();
        entity.setLogradouro(object.getLogradouro());
        entity.setCep(object.getCep());
        entity.setNumero(object.getNumero());
        entity.setCidade(object.getCidade());
        entity.setEstado(object.getEstado());
        entity.setTpPrincipal(object.getTpPrincipal());
        return entity;
    }
}
