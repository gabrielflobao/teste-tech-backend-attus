package com.br.teste.attus.controller;

import com.br.teste.attus.commons.PessoaConstant;
import com.br.teste.attus.dto.EnderecoSaveDTO;
import com.br.teste.attus.dto.PessoaDTO;
import com.br.teste.attus.dto.PessoaSaveDTO;
import com.br.teste.attus.entity.Pessoa;
import com.br.teste.attus.exceptions.endereco.EnderecoPrincipalFoundException;
import com.br.teste.attus.exceptions.pessoa.PessoaNotFoundException;
import com.br.teste.attus.mapper.PessoaRequestMapper;
import com.br.teste.attus.mapper.PessoaResponseMapper;
import com.br.teste.attus.mapper.PessoaSaveRequestMapper;
import com.br.teste.attus.mapper.PessoaSaveResponseMapper;
import com.br.teste.attus.repository.PessoaRepository;
import com.br.teste.attus.service.PessoaService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.text.SimpleDateFormat;
import java.util.List;

import static com.br.teste.attus.commons.EnderecoConstant.createEnderecoSaveDTOLogradouroCepNumeroCidadeEstadoTipoPrincipalS;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willReturn;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@WebMvcTest(PessoaController.class)
public class PessoaControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    PessoaService pessoaService;
    @MockBean
    PessoaRepository pessoaRepository;
    @Test
    void getListPessoas_Sucessfull() throws Exception {
        List<PessoaDTO> dto = PessoaResponseMapper.toReponseList(List.of(PessoaConstant.createTestPessoaIdNomeDataNascimento()));
        given(pessoaService.findAll()).willReturn(dto);
        ResultActions response = mockMvc.perform(get("/pessoa/listar")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)));

        response.andExpect(MockMvcResultMatchers.status().isOk());
    }
    @Test
    void getListPessoas_Failed() throws Exception {
        List<PessoaDTO> dto = PessoaResponseMapper.toReponseList(List.of(PessoaConstant.createTestPessoaIdNomeDataNascimento()));
        given(pessoaService.findAll()).willThrow(PessoaNotFoundException.class);
        ResultActions response = mockMvc.perform(get("/pessoa/listar")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)));

        response.andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    void getListPessoasById_Sucessful() throws Exception{
        List<PessoaDTO> dto = PessoaResponseMapper.toReponseList(List.of(PessoaConstant.createTestPessoaIdNomeDataNascimento()));
        String id = "1";
        given(pessoaService.findAllById(List.of(1L))).willReturn(dto);
        ResultActions response = mockMvc.perform(get("/pessoa/listar/")
                .contentType(MediaType.APPLICATION_JSON)
                .param("ids", id));

        response.andExpect(MockMvcResultMatchers.status().isOk());


    }  @Test
    void getListPessoasById_Failed() throws Exception{
        given(pessoaService.findAllById(anyList())).willThrow(PessoaNotFoundException.class);
        String id = "1";
        ResultActions response = mockMvc.perform(get("/pessoa/listar/")
                .contentType(MediaType.APPLICATION_JSON)
                .param("ids", id));

        response.andExpect(MockMvcResultMatchers.status().isNotFound());

    }

    @Test
    void createPessoas_Sucessful() throws Exception {
       List<PessoaSaveDTO> dto = List.of(PessoaSaveResponseMapper.toResponse(PessoaConstant.createTestPessoaIdNomeDataNascimento()));
        List<Pessoa> entityDto = PessoaSaveRequestMapper.toRequestSaveList(dto);
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        given(pessoaService.saveLista(ArgumentMatchers.any())).willAnswer(invocation -> invocation.getArgument(0));
        ResultActions response = mockMvc.perform(post("/pessoa/cadastrar")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)));
        response.andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].nomeCompleto").value(dto.get(0).getNomeCompleto()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].dataNascimento").value(sdf.format(dto.get(0).getDataNascimento())));

    }

    @Test
    void createPessoas_Failed() throws Exception {

        PessoaSaveDTO pessoaSaveDTO = PessoaSaveResponseMapper.toResponse(PessoaConstant.createTestPessoaIdNomeDataNascimento());
        EnderecoSaveDTO enderecoSaveDTO1=createEnderecoSaveDTOLogradouroCepNumeroCidadeEstadoTipoPrincipalS();
        EnderecoSaveDTO enderecoSaveDTO2=createEnderecoSaveDTOLogradouroCepNumeroCidadeEstadoTipoPrincipalS();
        pessoaSaveDTO.setEnderecos(List.of(enderecoSaveDTO1,enderecoSaveDTO2));
        List<PessoaSaveDTO> dtoList = List.of(pessoaSaveDTO);
        given(pessoaService.saveLista(anyList())).willThrow(EnderecoPrincipalFoundException.class);
        ResultActions response = mockMvc.perform(post("/pessoa/cadastrar")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dtoList)));

        // Asserting the response expectations
        response.andExpect(MockMvcResultMatchers.status().isFound());

    }
    @Test
    void updatePessoas_sucessfull() throws Exception {

       Pessoa pessoa = PessoaConstant.createTestPessoaIdNomeDataNascimento();
       List<PessoaDTO> pessoaDTOS = PessoaResponseMapper.toReponseList(List.of(pessoa));
        given(pessoaService.updatePessoas(pessoaDTOS)).willReturn(pessoaDTOS);
        ResultActions response = mockMvc.perform(patch("/pessoa/update")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(pessoaDTOS)));

        // Asserting the response expectations
        response.andExpect(MockMvcResultMatchers.status().isOk());

    }

    @Test
    void updatePessoas_failed_ThorwsPessoaNotFoundException() throws Exception {
        given(pessoaService.updatePessoas(anyList())).willThrow(PessoaNotFoundException.class);
        ResultActions response = mockMvc.perform(patch("/pessoa/update")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(List.of())));

        // Asserting the response expectations
        response.andExpect(MockMvcResultMatchers.status().isNotFound());

    }


}
