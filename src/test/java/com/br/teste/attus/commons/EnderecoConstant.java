package com.br.teste.attus.commons;

import com.br.teste.attus.dto.EnderecoDTO;
import com.br.teste.attus.dto.EnderecoSaveDTO;
import com.br.teste.attus.entity.Endereco;
import com.br.teste.attus.entity.Pessoa;
import com.br.teste.attus.enuns.EstadoBrasil;
import com.br.teste.attus.enuns.TipoPrincipal;

import static com.br.teste.attus.commons.PessoaConstant.PESSOA;

public class EnderecoConstant {
    public static final Endereco ENDERECO = new Endereco(1L, "Rua São José", "29175255", 33, "Serra", EstadoBrasil.ESPIRITO_SANTO, TipoPrincipal.S,
            PESSOA);


    public static EnderecoSaveDTO createEnderecoSaveDTOLogradouroCepNumeroCidadeEstadoTipoPrincipalN() {
        String logradouro = "123 Main St";
        String cep = "12345-678";
        Integer numero = 10;
        String cidade = "Cityville";
        EstadoBrasil estado = EstadoBrasil.ACRE;
        TipoPrincipal tpPrincipal = TipoPrincipal.N;

        return new EnderecoSaveDTO(logradouro, cep, numero, cidade, tpPrincipal, estado);
    }


    public static EnderecoSaveDTO createEnderecoSaveDTOLogradouroCepNumeroCidadeEstadoTipoPrincipalS() {
        String logradouro = "123 Main St";
        String cep = "12345-678";
        Integer numero = 10;
        String cidade = "Cityville";
        EstadoBrasil estado = EstadoBrasil.ACRE;
        TipoPrincipal tpPrincipal = TipoPrincipal.S;

        return new EnderecoSaveDTO(logradouro, cep, numero, cidade, tpPrincipal, estado);
    }

    public static EnderecoDTO createEnderecoDTOLogradouroCepNumeroCidadeEstadoTipoPrincipalN(Long id ) {
        String logradouro = "123 Main St";
        String cep = "12345-678";
        Integer numero = 10;
        String cidade = "Cityville";
        EstadoBrasil estado = EstadoBrasil.ACRE;
        TipoPrincipal tpPrincipal = TipoPrincipal.N;

        return new EnderecoDTO(id,logradouro, cep, numero, cidade, tpPrincipal, estado);
    }
    public static EnderecoDTO createEnderecoDTOLogradouroCepNumeroCidadeEstadoTipoPrincipalS(Long id ) {
        String logradouro = "123 Main St";
        String cep = "12345-678";
        Integer numero = 10;
        String cidade = "Cityville";
        EstadoBrasil estado = EstadoBrasil.ACRE;
        TipoPrincipal tpPrincipal = TipoPrincipal.S;

        return new EnderecoDTO(id,logradouro, cep, numero, cidade, tpPrincipal, estado);
    }


    public static Endereco createEnderecoIdLogradouroCepNumeroCidadeEstadoTipoPrincipalSPessoaParamater(Pessoa pessoa) {

    Long enderecoId = 1L;
    String logradouro = "123 Main St";
    String cep = "12345-678";
    Integer numero = 10;
    String cidade = "Cityville";
    EstadoBrasil estado = EstadoBrasil.ACRE;

    return new Endereco(enderecoId, logradouro, cep, numero, cidade, estado, TipoPrincipal.S, pessoa);

    }

    public static Endereco createEnderecoIdLogradouroCepNumeroCidadeEstadoTipoPrincipalN() {

    Long enderecoId = 1L;
    String logradouro = "123 Main St";
    String cep = "12345-678";
    Integer numero = 10;
    String cidade = "Cityville";
    EstadoBrasil estado = EstadoBrasil.ACRE;

    return new Endereco(enderecoId, logradouro, cep, numero, cidade, estado, TipoPrincipal.S);

    }

    public static Endereco createEnderecoIdLogradouroCepNumeroCidadeEstadoTipoPrincipalS() {

    Long enderecoId = 1L;
    String logradouro = "123 Main St";
    String cep = "12345-678";
    Integer numero = 10;
    String cidade = "Cityville";
    EstadoBrasil estado = EstadoBrasil.ACRE;

    return new Endereco(enderecoId, logradouro, cep, numero, cidade, estado, TipoPrincipal.S);

    }
}
