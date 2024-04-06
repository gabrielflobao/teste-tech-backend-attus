package com.br.teste.attus.commons;

import com.br.teste.attus.dto.EnderecoDTO;
import com.br.teste.attus.dto.PessoaDTO;
import com.br.teste.attus.enuns.EstadoBrasil;
import com.br.teste.attus.enuns.TipoPrincipal;


import static com.br.teste.attus.utils.DateUtils.parseDate;

public class EnderecoDTOConstant {

    public static final PessoaDTO PESSOA_DTO = new PessoaDTO(1L,"Gabriel Lobão", parseDate("24/05/2000"));

}
