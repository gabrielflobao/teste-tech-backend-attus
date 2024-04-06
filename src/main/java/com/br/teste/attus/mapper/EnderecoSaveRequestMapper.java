package com.br.teste.attus.mapper;

import com.br.teste.attus.dto.EnderecoDTO;
import com.br.teste.attus.dto.EnderecoSaveDTO;
import com.br.teste.attus.entity.Endereco;
import com.br.teste.attus.entity.Pessoa;
import java.util.List;
import java.util.stream.Collectors;

public class EnderecoSaveRequestMapper {

    public static List<Endereco> toRequestListSave(List<EnderecoSaveDTO> enderecos, Pessoa pessoa) {

        return enderecos.stream()
                .map(endereco -> EnderecoSaveRequestMapper.toRequest(endereco, pessoa))
                .collect(Collectors.toList());
    }



    public static Endereco toRequest(EnderecoSaveDTO object,Pessoa pessoa) {
        Endereco entity = new Endereco();
        entity.setLogradouro(object.getLogradouro());
        entity.setCep(object.getCep());
        entity.setNumero(object.getNumero());
        entity.setCidade(object.getCidade());
        entity.setEstado(object.getEstado());
        entity.setTpPrincipal(object.getTpPrincipal());
        entity.setPessoa(pessoa);
        return entity;
    }
}
