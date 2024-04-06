package com.br.teste.attus.commons;

import com.br.teste.attus.entity.Endereco;
import com.br.teste.attus.entity.Pessoa;
import com.br.teste.attus.utils.DateUtils;

import static com.br.teste.attus.commons.EnderecoConstant.ENDERECO;

import java.util.Collections;
import java.util.List;

import static com.br.teste.attus.utils.DateUtils.parseDate;

public class PessoaConstant {
    public static final Pessoa PESSOA = new Pessoa(1L,"Gabriel Lobão", parseDate("24/05/2000"), Collections.singletonList(ENDERECO));

    public static Pessoa createTestPessoaIdNomeDataNascimento() {
        java.sql.Date birthDate = DateUtils.parseDate("24/05/2000");
        return new Pessoa(1L,"Gabriel Figueiredo",birthDate, List.of(new Endereco()));
    }

}
