package com.br.teste.attus.mapper;

import com.br.teste.attus.dto.PessoaDTO;
import com.br.teste.attus.dto.PessoaSaveDTO;
import com.br.teste.attus.entity.Pessoa;

import java.util.List;
import java.util.stream.Collectors;

public class PessoaSaveRequestMapper {

    public static Pessoa toRequestSave(PessoaSaveDTO object) {
        Pessoa entity = new Pessoa();
        entity.setNomeCompleto(object.getNomeCompleto());
        entity.setDataNascimento(object.getDataNascimento());
        if (object.getEnderecos() != null) {
            entity.setEnderecos(EnderecoSaveRequestMapper.toRequestListSave(object.getEnderecos(), entity));
        }
        return entity;
    }

    public static List<Pessoa> toRequestSaveList(List<PessoaSaveDTO> object) {
       return object.stream().map( ob -> toRequestSave(ob)).collect(Collectors.toList());

    }
}
