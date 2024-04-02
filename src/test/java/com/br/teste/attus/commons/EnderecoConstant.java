package com.br.teste.attus.commons;

import com.br.teste.attus.dto.EnderecoDTO;
import com.br.teste.attus.entity.Endereco;
import com.br.teste.attus.enuns.EstadoBrasil;
import com.br.teste.attus.enuns.TipoPrincipal;
import com.br.teste.attus.mapper.PessoaMapper;

import static com.br.teste.attus.commons.PessoaConstant.PESSOA;

public class EnderecoConstant {
    public static final Endereco ENDERECO = new Endereco(1L,"Rua São José","29175255",33,"Serra", EstadoBrasil.ESPIRITO_SANTO, TipoPrincipal.S,
               PESSOA);
}
