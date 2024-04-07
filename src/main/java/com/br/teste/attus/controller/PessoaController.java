package com.br.teste.attus.controller;

import com.br.teste.attus.dto.EnderecoDTO;
import com.br.teste.attus.dto.PessoaDTO;
import com.br.teste.attus.dto.PessoaSaveDTO;
import com.br.teste.attus.service.EnderecoService;
import com.br.teste.attus.service.PessoaService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Author : Gabriel F F Lobão
 */
@RestController
@RequestMapping("/pessoa")
public class PessoaController {
    @Autowired
    private PessoaService pessoaService;

    @Operation(summary = "Lista pessoa(s)" +
            ".",method = "GET")
    @GetMapping("/listar")
    public ResponseEntity<List<PessoaDTO>> getListPessoas() {
        List<PessoaDTO> pessoas = pessoaService.findAll();
        return ResponseEntity.ok(pessoas);
    }
    @Operation(summary = "Buscar pessoa(s) pelo ID(s)" +
            ".",method = "GET")
    @GetMapping("/listar/")
    public ResponseEntity<List<PessoaDTO>> getListPessoasById(@RequestParam("ids") List<Long> ids) {
        List<PessoaDTO> pessoas = pessoaService.findAllById(ids);
        return ResponseEntity.ok(pessoas);
    }
    @Operation(summary = "Cadastra pessoa(s)" +
            ".",method = "POST")
    @PostMapping("/cadastrar")
    public ResponseEntity<List<PessoaSaveDTO>> createPessoas(@RequestBody List<PessoaSaveDTO> pessoasEnderecos) {
         List<PessoaSaveDTO> response = pessoaService.saveLista(pessoasEnderecos);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @Operation(summary = "Alterar endereços por meio da Pessoa" +
            ".", method = "PATCH")
    @PatchMapping(value = "/update")
    public ResponseEntity<List<PessoaDTO>> updatePessoas(@RequestBody List<PessoaDTO> lista) {
        return ResponseEntity.ok(pessoaService.updatePessoas(lista));

    }


}
