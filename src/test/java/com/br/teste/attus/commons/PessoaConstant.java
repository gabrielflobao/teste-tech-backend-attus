package com.br.teste.attus.commons;

import com.br.teste.attus.entity.Pessoa;
import static com.br.teste.attus.commons.EnderecoConstant.ENDERECO;

import java.util.Collections;

import static com.br.teste.attus.utils.DateUtils.parseDate;

public class PessoaConstant {
    public static final Pessoa PESSOA = new Pessoa(1L,"Gabriel Lobão", parseDate("24/05/2000"), Collections.singletonList(ENDERECO));
}
