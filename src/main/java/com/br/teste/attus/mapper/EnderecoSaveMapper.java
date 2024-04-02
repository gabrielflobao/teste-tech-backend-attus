package com.br.teste.attus.mapper;

import com.br.teste.attus.dto.EnderecoDTO;
import com.br.teste.attus.dto.EnderecoSaveDTO;
import com.br.teste.attus.entity.Endereco;
import com.br.teste.attus.entity.Pessoa;

public class EnderecoSaveMapper {

    public static Endereco toRequest(EnderecoSaveDTO object, Pessoa pessoa) {
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
