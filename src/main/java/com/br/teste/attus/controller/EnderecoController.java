package com.br.teste.attus.controller;

import com.br.teste.attus.dto.EnderecoDTO;
import com.br.teste.attus.entity.Endereco;
import com.br.teste.attus.service.EnderecoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * Author : Gabriel F F Lobão
 */
@RestController
@RequestMapping("/endereco")
public class EnderecoController {
    @Autowired
    private EnderecoService service;

    @GetMapping("/listar")
    public ResponseEntity<List<EnderecoDTO>> listaEnderecos() {
        List<EnderecoDTO> enderecos = service.findAll();
        return ResponseEntity.ok(enderecos);
    }

    @GetMapping("/buscar/{id}")
    public ResponseEntity<EnderecoDTO> buscaEnderecoId(@PathVariable("id") Long id) {
        EnderecoDTO endereco = service.findById(id);
        return ResponseEntity.ok(endereco);
    }

    @GetMapping("/buscar/pessoa/{id}")
    public List<EnderecoDTO> buscarEnderecosPorPessoa(@PathVariable("id") Long id) {
        List<EnderecoDTO> endereco = service.findByPessoaId(id);
        return endereco;
    }


    @GetMapping("/buscarEnderecos")
    public ResponseEntity<List<EnderecoDTO>> buscarEnderecosPorIds(@RequestParam List<Long> ids) {
        List<EnderecoDTO> enderecos = service.findAllById(ids);
        return ResponseEntity.ok(enderecos);
    }

    @PostMapping("/cadastrar")
    public ResponseEntity<EnderecoDTO> cadastrarEndereco(@RequestBody EnderecoDTO cadastroEndereco) {
        EnderecoDTO enderecoDTO = service.save(cadastroEndereco);
        return ResponseEntity.status(HttpStatus.CREATED).body(enderecoDTO);

    }

    @PostMapping("/cadastrarEnderecos")
    public ResponseEntity cadastrarEnderecos(@RequestBody List<EnderecoDTO> cadastroEnderecos) {
        cadastroEnderecos.forEach(enderecoDTO -> service.save(enderecoDTO));
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PutMapping(value = "/principal/{id}")
    public ResponseEntity<EnderecoDTO> definirEnderecoPrincipal(@PathVariable("id") Long id) {
        return ResponseEntity.ok(service.defineEnderecoPrincipal(id));

    }


}
