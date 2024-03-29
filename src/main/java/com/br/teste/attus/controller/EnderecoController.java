package com.br.teste.attus.controller;

import com.br.teste.attus.dto.EnderecoDTO;
import com.br.teste.attus.service.EnderecoService;
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
@RequestMapping("/endereco")
public class EnderecoController {
    @Autowired
    private EnderecoService service;

    @Operation(summary = "Realiza a listagem de endereços.",method = "GET")
    @GetMapping("/listar")
    public ResponseEntity<List<EnderecoDTO>> listaEnderecos() {
        List<EnderecoDTO> enderecos = service.findAll();
        return ResponseEntity.ok(enderecos);
    }
    @Operation(summary = "Busca endereço pelo ID.",method = "GET")
    @GetMapping("/buscar/{id}")
    public ResponseEntity<EnderecoDTO> buscaEnderecoId(@PathVariable("id") Long id) {
        EnderecoDTO endereco = service.findById(id);
        return ResponseEntity.ok(endereco);
    }
    @Operation(summary = "Busca endereço pelo ID pessoa.",method = "GET")
    @GetMapping("/buscar/pessoa/{id}")
    public List<EnderecoDTO> buscarEnderecosPorPessoa(@PathVariable("id") Long id) {
        List<EnderecoDTO> endereco = service.findByPessoaId(id);
        return endereco;
    }

    @Operation(summary = "Busca endereços pelos IDs" +
            ".",method = "GET")
    @GetMapping("/buscarEnderecos")
    public ResponseEntity<List<EnderecoDTO>> buscarEnderecosPorIds(@RequestParam List<Long> ids) {
        List<EnderecoDTO> enderecos = service.findAllById(ids);
        return ResponseEntity.ok(enderecos);
    }


    @PostMapping("/cadastrar")
    public ResponseEntity cadastrarEnderecos(@RequestBody List<EnderecoDTO> cadastroEnderecos) {
        cadastroEnderecos.forEach(enderecoDTO -> service.save(enderecoDTO));
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PutMapping(value = "/principal/{id}")
    public ResponseEntity<EnderecoDTO> definirEnderecoPrincipal(@PathVariable("id") Long id) {
        return ResponseEntity.ok(service.defineEnderecoPrincipal(id));

    }

    @PutMapping(value = "/cadastrar/pessoa/{id}")
    public ResponseEntity<EnderecoDTO> cadastrarEnderecoPessoa(@PathVariable("id") Long id,@RequestBody List<EnderecoDTO> cadastroEnderecos) {
        cadastroEnderecos.forEach(enderecoDTO -> service.save(enderecoDTO));
        return ResponseEntity.status(HttpStatus.CREATED).build();

    }


}
