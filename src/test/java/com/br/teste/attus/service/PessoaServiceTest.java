package com.br.teste.attus.service;

import com.br.teste.attus.dto.PessoaDTO;
import com.br.teste.attus.dto.PessoaSaveDTO;
import com.br.teste.attus.entity.Pessoa;
import com.br.teste.attus.exceptions.pessoa.PessoaNotFoundException;
import com.br.teste.attus.mapper.PessoaResponseMapper;
import com.br.teste.attus.mapper.PessoaSaveResponseMapper;
import com.br.teste.attus.repository.PessoaRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.br.teste.attus.commons.PessoaConstant.createTestPessoaIdNomeDataNascimento;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PessoaServiceTest {

    @InjectMocks
    private PessoaService pessoaService;
    @Mock
    private PessoaRepository pessoaRepository;
    @Mock
    private PessoaSaveResponseMapper mapper;
    @Test
    void save_Sucessfull() {
        Pessoa pessoa = createTestPessoaIdNomeDataNascimento();
        PessoaSaveDTO pessoaSaveDTO = mapper.toResponse(pessoa);
        doReturn(pessoa).when(pessoaRepository).save(any(Pessoa.class));
        assertThat(pessoaService.save(pessoaSaveDTO)).isEqualTo(pessoaSaveDTO);
    }

    @Test
    void saveLista_Sucessfull() {
        List<Pessoa> pessoas = List.of(createTestPessoaIdNomeDataNascimento());
        List<PessoaSaveDTO> pessoasDTO = List.of(PessoaSaveResponseMapper.toResponse(createTestPessoaIdNomeDataNascimento()));
        assertThat(pessoaService.saveLista(pessoasDTO).containsAll(pessoasDTO));
    }

    @Test
    void findAll_ThrowsPessoaNotFoundException() {
        when(pessoaRepository.findAll()).thenReturn(List.of());
        Assertions.assertThatThrownBy(() -> pessoaService.findAll()).isInstanceOf(PessoaNotFoundException.class);

    }   @Test
    void findAll_Sucessful() {
        Pessoa pessoaRequestTest = createTestPessoaIdNomeDataNascimento();
        List<Pessoa> listRequestTest = new ArrayList<>();
        listRequestTest.add(pessoaRequestTest);
        when(pessoaRepository.findAll()).thenReturn(List.of(pessoaRequestTest));
        Assertions.assertThat(pessoaService.findAll().containsAll(PessoaResponseMapper.toReponseList(listRequestTest)));
    }

    @Test
    void findById_Sucessful() {
        Pessoa pessoaRequestTest = createTestPessoaIdNomeDataNascimento();
        when(pessoaRepository.findById(pessoaRequestTest.getId())).thenReturn(Optional.of(pessoaRequestTest));
        Assertions.assertThat(pessoaService.findById(pessoaRequestTest.getId()).equals(PessoaResponseMapper.toReponse(pessoaRequestTest)));
    }
    @Test
    void findById_Throws_PessoaNotFoundException() {
        when(pessoaRepository.findById(any())).thenReturn(Optional.empty());
        Assertions.assertThatThrownBy(() -> pessoaService.findById(any())).isInstanceOf(PessoaNotFoundException.class);
    }


    @Test
    void findAllById_Sucessful() {
        PessoaDTO pessoaResponseTest = PessoaResponseMapper.toReponse(createTestPessoaIdNomeDataNascimento());
        Pessoa pessoaRequestTest = createTestPessoaIdNomeDataNascimento();
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
        Pessoa pessoa = createTestPessoaIdNomeDataNascimento();
        List<Pessoa> pessoasLista = new ArrayList<>();
        pessoasLista.add(pessoa);

        List<PessoaDTO> pessoaAlterada = PessoaResponseMapper.toReponseList(pessoasLista);
        when(pessoaRepository.findAllById(List.of(pessoa.getId()))).thenReturn(pessoasLista);
        pessoasLista.get(0).setNomeCompleto("Lobao");
        when(pessoaRepository.saveAll(List.of(pessoa))).thenReturn(pessoasLista);
        List<PessoaDTO> result = pessoaService.updatePessoas(PessoaResponseMapper.toReponseList(pessoasLista));
        Assertions.assertThat(result.containsAll(List.of(pessoaAlterada)));
    }
}