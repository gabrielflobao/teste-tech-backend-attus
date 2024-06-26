package com.br.teste.attus.service;

import com.br.teste.attus.commons.PessoaConstant;
import com.br.teste.attus.dto.EnderecoDTO;
import com.br.teste.attus.dto.EnderecoSaveDTO;
import com.br.teste.attus.entity.Endereco;
import com.br.teste.attus.entity.Pessoa;
import com.br.teste.attus.enuns.EstadoBrasil;
import com.br.teste.attus.enuns.TipoPrincipal;
import com.br.teste.attus.exceptions.endereco.EnderecoNotFoundException;
import com.br.teste.attus.exceptions.endereco.EnderecoPrincipalFoundException;
import com.br.teste.attus.exceptions.endereco.EnderecoPrincipalNotFound;
import com.br.teste.attus.exceptions.pessoa.PessoaNotFoundException;
import com.br.teste.attus.mapper.EnderecoResponseMapper;
import com.br.teste.attus.repository.EnderecoRepository;
import com.br.teste.attus.repository.PessoaRepository;
import org.assertj.core.api.AbstractThrowableAssert;
import org.assertj.core.api.Assertions;
import org.assertj.core.api.ThrowableAssert;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static com.br.teste.attus.commons.EnderecoConstant.*;
import static com.br.teste.attus.commons.PessoaConstant.createTestPessoaIdNomeDataNascimento;
import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class EnderecoServiceTest {

    @InjectMocks
    private EnderecoService enderecoService;
    @Mock
    private EnderecoRepository enderecoRepository;

    @Mock
    private PessoaRepository pessoaRepository;

    @Mock
    private PessoaService pessoaService;

    @Mock
    private EnderecoResponseMapper mapper;


    @Test
    public void createEndereco_WithValidData_ReturnsEndereco() {
        when(enderecoRepository.save(ENDERECO)).thenReturn(ENDERECO);
        Endereco endereco = enderecoRepository.save(ENDERECO);
        org.assertj.core.api.Assertions.assertThat(endereco).isEqualTo(ENDERECO);
    }

    @Test
    public void findEndereco_NotExist() {
        Optional<Endereco> endereco = Optional.empty();
        when(enderecoRepository.findById(1L)).thenReturn(endereco);
        Assertions.assertThatThrownBy(() -> enderecoService.findById(1L)).isInstanceOf(EnderecoNotFoundException.class);
    }

    @Test
    public void findAllEnderecoThrowsEnderecoNotFoundException() {
        when(enderecoRepository.findAll()).thenReturn(List.of());
        Assertions.assertThatThrownBy(() -> enderecoService.findAll()).isInstanceOf(EnderecoNotFoundException.class);
    }

    @Test
    public void findAllByIdEnderecoEnderecoNotFoundException() {

        List<Long> ids = List.of(1L);
        when(enderecoRepository.findAllById(ids)).thenReturn(List.of());
        Assertions.assertThatThrownBy(() -> enderecoService.findAllById(ids)).isInstanceOf(EnderecoNotFoundException.class);
        EnderecoNotFoundException ex =
                org.junit.jupiter.api.Assertions.assertThrows(EnderecoNotFoundException.class, ()
                        -> enderecoService.findAllById(ids));

    }

    @Test
    public void findByPessoaIdThrowsEnderecoNotFoundException() {
        when(enderecoRepository.findByPessoaId(1L)).thenReturn(List.of());
        Assertions.assertThatThrownBy(() -> enderecoService.findByPessoaId(1L)).isInstanceOf(EnderecoNotFoundException.class);
    }
    @Test
    public void findByPessoaIdSucessful() {
        List<Endereco> lista = new ArrayList<>();
        lista.add(ENDERECO);
        when(enderecoRepository.findByPessoaId(1L)).thenReturn(lista);
        Assertions.assertThat(enderecoService.findByPessoaId(1L).containsAll(lista));
    }

    @Test
    public void getResponseSaveEnderecosByIdPessoa_Sucessfull() {
        // Mock data
        EnderecoSaveDTO enderecoSaveDTO1 = new EnderecoSaveDTO(
                "Rua Donaldislon",
                "29175225",
                403,
                "SERRA",
                TipoPrincipal.N,
                EstadoBrasil.ESPIRITO_SANTO
        );
        EnderecoSaveDTO enderecoSaveDTO2 = new EnderecoSaveDTO(
                "Rua Donaldislon",
                "29175225",
                403,
                "SERRA",
                TipoPrincipal.N,
                EstadoBrasil.ESPIRITO_SANTO
        );
        List<EnderecoSaveDTO> endereco = new ArrayList<>();
        endereco.add(enderecoSaveDTO1);
        endereco.add(enderecoSaveDTO2);
        // Mock method responses
        when(pessoaService.existsPessoaById(PessoaConstant.PESSOA.getId())).thenReturn(true);
        when(pessoaRepository.findById(PessoaConstant.PESSOA.getId())).thenReturn(Optional.of(PessoaConstant.PESSOA));
        Assertions.assertThat(enderecoService.saveAllByIdPessoa(PessoaConstant.PESSOA.getId(),endereco).containsAll(endereco));



    }

    @Test
    public void getResponseSaveEnderecosByIdPessoa_ThrowsPessoaNotFoundException() {
        // Mock data
        EnderecoSaveDTO enderecoSaveDTO1 = new EnderecoSaveDTO(
                "Rua Donaldislon",
                "29175225",
                403,
                "SERRA",
                TipoPrincipal.N,
                EstadoBrasil.ESPIRITO_SANTO
        );
        EnderecoSaveDTO enderecoSaveDTO2 = new EnderecoSaveDTO(
                "Rua Donaldislon",
                "29175225",
                403,
                "SERRA",
                TipoPrincipal.N,
                EstadoBrasil.ESPIRITO_SANTO
        );
        List<EnderecoSaveDTO> endereco = new ArrayList<>();
        endereco.add(enderecoSaveDTO1);
        endereco.add(enderecoSaveDTO2);
        // Mock method responses
        when(pessoaService.existsPessoaById(PessoaConstant.PESSOA.getId())).thenReturn(false);

        ThrowableAssert.ThrowingCallable callableUnderTestMethod = () -> enderecoService.saveAllByIdPessoa(PessoaConstant.PESSOA.getId(),endereco);
        // Then
        Assertions.assertThatThrownBy(callableUnderTestMethod).isInstanceOf(PessoaNotFoundException.class);


    }


    @Test
    public void updateEnderecoPrincipalToNThrowsEnderecoPrincipalNotFound() {
        Long providedId = 1L;
        Optional.empty();

        when(enderecoRepository.findPrincipalEndereco(providedId, TipoPrincipal.S)).thenReturn(Optional.empty());

        ThrowableAssert.ThrowingCallable updateEnderecoCallable = () -> enderecoService.updateEnderecoPrincipalToN(providedId);
        AbstractThrowableAssert<?, ? extends Throwable> ex =
                Assertions.assertThatThrownBy(updateEnderecoCallable)
                        .isInstanceOf(EnderecoPrincipalNotFound.class);
    }

    @Test
    public void updateEnderecoPrincipalToN_Sucessful() {
        Long providedId = 1L;
        Endereco enderecoEntity = createEnderecoIdLogradouroCepNumeroCidadeEstadoTipoPrincipalS();
        Optional<Endereco> endereco = Optional.of(enderecoEntity);
        EnderecoDTO enderecoResponse = createEnderecoDTOLogradouroCepNumeroCidadeEstadoTipoPrincipalN(providedId);
        when(enderecoRepository.findPrincipalEndereco(providedId, TipoPrincipal.S)).thenReturn(endereco);
        when(enderecoRepository.save(endereco.get())).thenReturn(enderecoEntity);
        EnderecoDTO result = enderecoService.updateEnderecoPrincipalToN(providedId);
        org.junit.jupiter.api.Assertions.assertEquals(TipoPrincipal.N, result.getTpPrincipal());
    }

    @Test
    public void buscarEnderecoPorPessoaThrowsEnderecoPrincipalNotFound() {
        // Given
        long pessoaId = 99L;
        when(enderecoRepository.findByPessoaId(pessoaId)).thenReturn(List.of());

        // When
        ThrowableAssert.ThrowingCallable findEnderecoByPessoaIdCallable = () -> enderecoService.findByPessoaId(pessoaId);

        EnderecoNotFoundException ex =
                org.junit.jupiter.api.Assertions.assertThrows(EnderecoNotFoundException.class, ()
                        -> enderecoService.findByPessoaId(pessoaId));

    }
    @Test
    public void defineEnderecoPrincipal_ThrowsEnderecoPrincipalFoundException_WhenPrincipalAddressAlreadyExists() {
        // Given
        when(enderecoRepository.findById(anyLong())).thenReturn(Optional.of(ENDERECO));
        when(enderecoRepository.findPessoasEndecerosByIdEndereco(anyLong())).thenReturn(List.of(ENDERECO));
        long pessoaId = ENDERECO.getId();

        // When
        ThrowableAssert.ThrowingCallable callableUnderTestMethod = () -> enderecoService.defineEnderecoPrincipal(pessoaId);

        // Then
        Assertions.assertThatThrownBy(callableUnderTestMethod).isInstanceOf(EnderecoPrincipalFoundException.class);
    }
    @Test
    public void defineEnderecoPrincipal_ThrowsEnderecoNotFoundException_WhenNotExistsEndereco() {
        // Given
        when(enderecoRepository.findById(anyLong())).thenReturn(Optional.empty());
        long pessoaId = ENDERECO.getId();

        // When
        ThrowableAssert.ThrowingCallable callableUnderTestMethod = () -> enderecoService.defineEnderecoPrincipal(pessoaId);

        // Then
        Assertions.assertThatThrownBy(callableUnderTestMethod).isInstanceOf(EnderecoNotFoundException.class);
    }

    @Test
    public void defineEnderecoPrincipal_Sucessful() {
        // Given

        Endereco enderecoEntity = createEnderecoIdLogradouroCepNumeroCidadeEstadoTipoPrincipalN();
        when(enderecoRepository.findById(enderecoEntity.getId())).thenReturn(Optional.of(enderecoEntity));
        when( enderecoRepository.save(enderecoEntity)).thenReturn(enderecoEntity);

        org.junit.jupiter.api.Assertions.assertEquals(enderecoEntity.getId(),enderecoService.defineEnderecoPrincipal(1L).getId());
        org.junit.jupiter.api.Assertions.assertEquals(enderecoEntity.getTpPrincipal(),enderecoService.defineEnderecoPrincipal(1L).getTpPrincipal());
        org.junit.jupiter.api.Assertions.assertEquals(enderecoEntity.getCep(),enderecoService.defineEnderecoPrincipal(1L).getCep());
    }
    @Test
    public void saveAll_Sucessful() {
        // Given
        Pessoa pessoa = createTestPessoaIdNomeDataNascimento();
        Long pessoaId = 1L;
        List<EnderecoDTO> enderecoDTOs = new ArrayList<>();
        EnderecoSaveDTO enderecoSaveDTO1 = createEnderecoSaveDTOLogradouroCepNumeroCidadeEstadoTipoPrincipalN();
        EnderecoSaveDTO enderecoSaveDTO2 = createEnderecoSaveDTOLogradouroCepNumeroCidadeEstadoTipoPrincipalN();
        Assertions.assertThat(enderecoService.saveAll(List.of(enderecoSaveDTO1, enderecoSaveDTO2), pessoa).containsAll(enderecoDTOs));

    }





    @Test
    public void updateEnderecos_ThrowEnderecoNotFoundException() {
        Pessoa pessoa = createTestPessoaIdNomeDataNascimento();
        Endereco saveEndereco = createEnderecoIdLogradouroCepNumeroCidadeEstadoTipoPrincipalN();
        saveEndereco.setPessoa(pessoa);
        pessoa.setEnderecos(List.of(saveEndereco));
        List<Endereco> enderecos = new ArrayList<>();
        enderecos.add(saveEndereco);
        EnderecoDTO expectedEnderecoDTO = createEnderecoDTOLogradouroCepNumeroCidadeEstadoTipoPrincipalS(1L);
        List<EnderecoDTO> expectedResponse = List.of(expectedEnderecoDTO);
        ThrowableAssert.ThrowingCallable callableUnderTestMethod = () -> enderecoService.updateEnderecos(expectedResponse);
        Assertions.assertThatThrownBy(callableUnderTestMethod).isInstanceOf(EnderecoNotFoundException.class);

    }

    @Test
    public void updateEnderecos_Sucessfull() {
        Pessoa pessoaEndereco = createTestPessoaIdNomeDataNascimento();
        Endereco saveEndereco = createEnderecoIdLogradouroCepNumeroCidadeEstadoTipoPrincipalS();
        saveEndereco.setPessoa(pessoaEndereco);
        pessoaEndereco.setEnderecos(List.of(saveEndereco));
        EnderecoDTO dto = createEnderecoDTOLogradouroCepNumeroCidadeEstadoTipoPrincipalS(1L);
        List<EnderecoDTO> dtos = new ArrayList<>();
        dtos.add(dto);
        when(enderecoRepository.findAllById(any())).thenReturn(List.of(saveEndereco));
        when(enderecoRepository.saveAll(List.of(saveEndereco))).thenReturn(List.of(saveEndereco));
        Assertions.assertThat(enderecoService.updateEnderecos(dtos).containsAll((List.of(saveEndereco))));


    }
    @Test
    public void validateExistingPrincipalThrowsEnderecoPrincipalFoundException() {
        Pessoa pessoa = createTestPessoaIdNomeDataNascimento();
        Long pessoaId = 1L;
        List<EnderecoDTO> enderecoDTOs = new ArrayList<>();
        EnderecoSaveDTO enderecoSaveDTO1 = createEnderecoSaveDTOLogradouroCepNumeroCidadeEstadoTipoPrincipalS();
        EnderecoSaveDTO enderecoSaveDTO2 = createEnderecoSaveDTOLogradouroCepNumeroCidadeEstadoTipoPrincipalS();
        Endereco saveEndereco = createEnderecoIdLogradouroCepNumeroCidadeEstadoTipoPrincipalSPessoaParamater(pessoa);
        when(enderecoRepository.findEnderecoByTpPrincipalSim(1L)).thenReturn(Optional.of(saveEndereco));
        ThrowableAssert.ThrowingCallable callableUnderTestMethod = () -> enderecoService.saveAll(List.of(enderecoSaveDTO1, enderecoSaveDTO2), pessoa);
        Assertions.assertThatThrownBy(callableUnderTestMethod).isInstanceOf(EnderecoPrincipalFoundException.class);

    }

}
