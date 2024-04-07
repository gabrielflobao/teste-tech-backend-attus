package com.br.teste.attus.controller;

import com.br.teste.attus.commons.EnderecoConstant;
import com.br.teste.attus.commons.PessoaConstant;
import com.br.teste.attus.dto.EnderecoDTO;
import com.br.teste.attus.dto.EnderecoSaveDTO;
import com.br.teste.attus.dto.PessoaDTO;
import com.br.teste.attus.entity.Endereco;
import com.br.teste.attus.entity.Pessoa;
import com.br.teste.attus.enuns.TipoPrincipal;
import com.br.teste.attus.exceptions.endereco.EnderecoNotFoundException;
import com.br.teste.attus.exceptions.endereco.EnderecoPrincipalFoundException;
import com.br.teste.attus.exceptions.endereco.EnderecoPrincipalNotFound;
import com.br.teste.attus.exceptions.pessoa.PessoaNotFoundException;
import com.br.teste.attus.mapper.EnderecoResponseMapper;
import com.br.teste.attus.mapper.EnderecoSaveResponseMapper;
import com.br.teste.attus.mapper.PessoaResponseMapper;
import com.br.teste.attus.repository.EnderecoRepository;
import com.br.teste.attus.repository.PessoaRepository;
import com.br.teste.attus.service.EnderecoService;
import com.br.teste.attus.service.PessoaService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;

import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@WebMvcTest(EnderecoController.class)
public class EnderecoControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    EnderecoService enderecoService;
    @MockBean
    EnderecoRepository enderecoRepository;

    @Test
    void listaEnderecosSucessful() throws Exception {
        List<EnderecoDTO> dto = EnderecoResponseMapper.toReponseList(List.of(EnderecoConstant.createEnderecoIdLogradouroCepNumeroCidadeEstadoTipoPrincipalS()));
        given(enderecoService.findAll()).willReturn(dto);
        ResultActions response = mockMvc.perform(get("/endereco/listar")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)));

        response.andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void listaEnderecosFailed() throws Exception {

        List<EnderecoDTO> dto = EnderecoResponseMapper.toReponseList(List.of(EnderecoConstant.createEnderecoIdLogradouroCepNumeroCidadeEstadoTipoPrincipalS()));
        given(enderecoService.findAll()).willThrow(EnderecoNotFoundException.class);
        ResultActions response = mockMvc.perform(get("/endereco/listar"));
        response.andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    void buscarEnderecosPorIds_Sucesssful() throws Exception {
        List<EnderecoDTO> dto = EnderecoResponseMapper.toReponseList(List.of(EnderecoConstant.createEnderecoIdLogradouroCepNumeroCidadeEstadoTipoPrincipalS()));
        String id = "1";
        given(enderecoService.findAllById(List.of(1L))).willReturn(dto);
        ResultActions response = mockMvc.perform(get("/endereco/listar/")
                .contentType(MediaType.APPLICATION_JSON)
                .param("ids", id));

        response.andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void buscarEnderecosPorIds_Failed_Throws_EnderecoNotFoudn() throws Exception {
        given(enderecoService.findAllById(anyList())).willThrow(EnderecoNotFoundException.class);
        String id = "1";
        ResultActions response = mockMvc.perform(get("/endereco/listar/")
                .contentType(MediaType.APPLICATION_JSON)
                .param("ids", id));

        response.andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    void findEnderecosPorPessoa_Sucessful() throws Exception {
        Endereco endereco = EnderecoConstant.createEnderecoIdLogradouroCepNumeroCidadeEstadoTipoPrincipalS();
        Pessoa pessoa = PessoaConstant.createTestPessoaIdNomeDataNascimento();
        endereco.setPessoa(pessoa);
        pessoa.setEnderecos(List.of(endereco));
        List<EnderecoDTO> dto = EnderecoResponseMapper.toReponseList(List.of(endereco));


        given(enderecoService.findByPessoaId(1L)).willReturn(dto);
        String id = "1";
        ResultActions response = mockMvc.perform(get("/endereco/listar/pessoa/{id}", id)
                .contentType(MediaType.APPLICATION_JSON));

        response.andExpect(MockMvcResultMatchers.status().isOk());

    }

    @Test
    void findEnderecosPorPessoa_Failed_Throws() throws Exception {
        given(enderecoService.findByPessoaId(1L)).willThrow(EnderecoNotFoundException.class);
        String id = "1";
        ResultActions response = mockMvc.perform(get("/endereco/listar/pessoa/{id}", id)
                .contentType(MediaType.APPLICATION_JSON));

        response.andExpect(MockMvcResultMatchers.status().isNotFound());

    }

    @Test
    void definirEnderecoPrincipal_Sucessful() throws Exception {
        Endereco endereco = EnderecoConstant.createEnderecoIdLogradouroCepNumeroCidadeEstadoTipoPrincipalS();
        Pessoa pessoa = PessoaConstant.createTestPessoaIdNomeDataNascimento();
        endereco.setPessoa(pessoa);
        pessoa.setEnderecos(List.of(endereco));
        EnderecoDTO dto = EnderecoResponseMapper.toReponse(endereco);

        given(enderecoService.defineEnderecoPrincipal(1L)).willReturn(dto);
        String id = "1";
        ResultActions response = mockMvc.perform(put("/endereco/principal/{id}", id)
                .contentType(MediaType.APPLICATION_JSON));
        response.andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void definirEnderecoPrincipal_Failed_ThrowsEnderecoPrincipalFoundException() throws Exception {
        given(enderecoService.defineEnderecoPrincipal(1L)).willThrow(EnderecoPrincipalFoundException.class);
        String id = "1";
        ResultActions response = mockMvc.perform(put("/endereco/principal/{id}", id)
                .contentType(MediaType.APPLICATION_JSON));
        response.andExpect(MockMvcResultMatchers.status().isFound());
    }

    @Test
    void cadastrarEnderecos_Sucessfull() throws Exception {
        Endereco endereco = EnderecoConstant.createEnderecoIdLogradouroCepNumeroCidadeEstadoTipoPrincipalS();
        Pessoa pessoa = PessoaConstant.createTestPessoaIdNomeDataNascimento();
        endereco.setPessoa(pessoa);
        pessoa.setEnderecos(List.of(endereco));
        EnderecoSaveDTO dto = EnderecoSaveResponseMapper.toResponse(endereco);
        EnderecoDTO dtoResponse = EnderecoResponseMapper.toReponse(endereco);

        given(enderecoService.saveAllByIdPessoa(1L, List.of(dto))).willReturn(List.of(dtoResponse));
        String id = "1";
        ResultActions response = mockMvc.perform(put("/endereco/cadastrar/pessoa/{id}", id)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(List.of(dto))));
        response.andExpect(MockMvcResultMatchers.status().isCreated());

    }

    @Test
    void cadastrarEnderecos_Failed_Throws_EnderecoNotFoundException() throws Exception {
        given(enderecoService.saveAllByIdPessoa(1L, List.of())).willThrow(EnderecoNotFoundException.class);
        String id = "1";
        ResultActions response = mockMvc.perform(put("/endereco/cadastrar/pessoa/{id}", id)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(List.of())));

        response.andExpect(MockMvcResultMatchers.status().isNotFound());

    }


    @Test
    void updateEnderecoPrincipalToN_Sucessfull() throws Exception {

        Endereco endereco = EnderecoConstant.createEnderecoIdLogradouroCepNumeroCidadeEstadoTipoPrincipalS();
        endereco.setTpPrincipal(TipoPrincipal.N);
        Pessoa pessoa = PessoaConstant.createTestPessoaIdNomeDataNascimento();
        endereco.setPessoa(pessoa);
        pessoa.setEnderecos(List.of(endereco));
        EnderecoDTO dtoResponse = EnderecoResponseMapper.toReponse(endereco);
        given(enderecoService.updateEnderecoPrincipalToN(1L)).willReturn(dtoResponse);
        String id = "1";
        ResultActions response = mockMvc.perform(put("/endereco/update/principal/")
                .param("id", id)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dtoResponse)));

        response.andExpect(MockMvcResultMatchers.status().isOk());

    }

    @Test
    void updateEnderecoPrincipalToN_Failed_ThrowsEnderecoPrincipalNotFound() throws Exception {

        given(enderecoService.updateEnderecoPrincipalToN(1L)).willThrow(EnderecoPrincipalNotFound.class);
        String id = "1";
        ResultActions response = mockMvc.perform(put("/endereco/update/principal/")
                .param("id", id)
                .contentType(MediaType.APPLICATION_JSON));
        response.andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    void updateEndereco_Sucessful() throws Exception {
        Endereco endereco = EnderecoConstant.createEnderecoIdLogradouroCepNumeroCidadeEstadoTipoPrincipalS();
        endereco.setTpPrincipal(TipoPrincipal.N);
        Pessoa pessoa = PessoaConstant.createTestPessoaIdNomeDataNascimento();
        endereco.setPessoa(pessoa);
        pessoa.setEnderecos(List.of(endereco));
        List<EnderecoDTO> dtoResponse = EnderecoResponseMapper.toReponseList(List.of(endereco));
        given(enderecoService.updateEnderecos(dtoResponse)).willReturn(dtoResponse);
        String id = "1";
        ResultActions response = mockMvc.perform(patch("/endereco/update")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dtoResponse)));
        response.andExpect(MockMvcResultMatchers.status().isOk());
    }

}
