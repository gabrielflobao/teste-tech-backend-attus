package com.br.teste.attus.service;

import com.br.teste.attus.dto.EnderecoDTO;
import com.br.teste.attus.dto.PessoaDTO;
import com.br.teste.attus.entity.Endereco;
import com.br.teste.attus.entity.Pessoa;
import com.br.teste.attus.enuns.EstadoBrasil;
import com.br.teste.attus.enuns.TipoPrincipal;
import com.br.teste.attus.exceptions.endereco.EnderecoNotFoundException;
import com.br.teste.attus.exceptions.pessoa.PessoaNotFoundException;
import com.br.teste.attus.mapper.PessoaMapper;
import com.br.teste.attus.repository.EnderecoRepository;
import com.br.teste.attus.repository.PessoaRepository;
import com.br.teste.attus.utils.DateUtils;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.br.teste.attus.utils.DateUtils.parseDate;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PessoaServiceTest {

    @InjectMocks
    private PessoaService pessoaService;
    @Mock
    private PessoaRepository pessoaRepository;

    @Test
    void save_Sucessfull() {
        PessoaDTO pessoaRequestTest = new PessoaDTO("Gabriel",parseDate("24/05/2000"),null);
        when(pessoaRepository.save(any(Pessoa.class))).thenReturn(new Pessoa());
        Assertions.assertThat(pessoaService.save(pessoaRequestTest).equals(pessoaRequestTest));
    }

    @Test
    void saveLista_Sucessfull() {
        PessoaDTO pessoaRequestTest = new PessoaDTO("Gabriel",parseDate("24/05/2000"),null);
        List<PessoaDTO> listRequestTest = new ArrayList<>();
        listRequestTest.add(pessoaRequestTest);
        when(pessoaRepository.saveAll(anyList())).thenReturn(List.of(new Pessoa()));
        Assertions.assertThat(pessoaService.saveLista(listRequestTest).equals(listRequestTest));
    }

    @Test
    void findAll_ThrowsPessoaNotFoundException() {
        when(pessoaRepository.findAll()).thenReturn(List.of());
        Assertions.assertThatThrownBy(() -> pessoaService.findAll()).isInstanceOf(PessoaNotFoundException.class);

    }   @Test
    void findAll_Sucessful() {
        PessoaDTO pessoaRequestTest = new PessoaDTO("Gabriel",parseDate("24/05/2000"),null);
        List<PessoaDTO> listRequestTest = new ArrayList<>();
        listRequestTest.add(pessoaRequestTest);
        when(pessoaRepository.findAll()).thenReturn(PessoaMapper.toRequestList(List.of(pessoaRequestTest)));
        Assertions.assertThat(pessoaService.findAll().containsAll(listRequestTest));
    }

    @Test
    void findById_Sucessful() {
        Pessoa pessoaRequestTest = new Pessoa(1L,"Gabriel",parseDate("24/05/2000"),null);
        when(pessoaRepository.findById(pessoaRequestTest.getId())).thenReturn(Optional.of(pessoaRequestTest));
        Assertions.assertThat(pessoaService.findById(pessoaRequestTest.getId()).equals(pessoaRequestTest));
    }
    @Test
    void findById_Throws_PessoaNotFoundException() {
        when(pessoaRepository.findById(any())).thenReturn(Optional.empty());
        Assertions.assertThatThrownBy(() -> pessoaService.findById(any())).isInstanceOf(PessoaNotFoundException.class);
    }


    @Test
    void findAllById_Sucessful() {
        PessoaDTO pessoaResponseTest = new PessoaDTO(1L,"Gabriel",parseDate("24/05/2000"));
        Pessoa pessoaRequestTest = new Pessoa(1L,"Gabriel",parseDate("24/05/2000"),null);
        when(pessoaRepository.findAllById(List.of(1L))).thenReturn(List.of(pessoaRequestTest));
        Assertions.assertThat(pessoaService.findAllById(List.of(pessoaRequestTest.getId())).equals(pessoaResponseTest));
    }

    @Test
    void existsPessoaById_False() {
        org.junit.jupiter.api.Assertions.assertFalse(pessoaService.existsPessoaById(1L));
    }

    @Test
    void existsPessoaById_true() {
        when(pessoaRepository.existsById(any())).thenReturn(true);
        org.junit.jupiter.api.Assertions.assertTrue(pessoaService.existsPessoaById(1L));
    }

    @Test
    void updatePessoas_sucessfull() {
        Long id = 1L;
        String logradouro = "123 Main St";
        String cep = "12345-678";
        Integer numero = 10;
        String cidade = "Cityville";
        EstadoBrasil estado = EstadoBrasil.ACRE;
        TipoPrincipal tpPrincipal = TipoPrincipal.S;
        Long providedId = 1L;
        String nomePessoa = "Gabriel Lobão";
        String nomeAlterado = "Gabriel F F Lobão";
        Date data = DateUtils.parseDate("24/05/2000");
        Pessoa pessoa = new Pessoa(id,nomePessoa,data,null);
        Optional<Pessoa> pessoaOptional = Optional.of(pessoa);
        PessoaDTO pessoaResponse = new PessoaDTO(id, nomeAlterado,data);
        List<Pessoa> pessoasLista = new ArrayList<>();
        pessoasLista.add(pessoa);
        when(pessoaRepository.findAllById(List.of(pessoa.getId()))).thenReturn(pessoasLista);
        when(pessoaRepository.saveAll(List.of(pessoa))).thenReturn(pessoasLista);
        List<PessoaDTO> result = pessoaService.updatePessoas(List.of(pessoaResponse));
        Assertions.assertThat(result.containsAll(List.of(pessoaResponse)));
    }
}