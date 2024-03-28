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
    private static final Logger log = LoggerFactory.getLogger(EnderecoController.class);

    @Autowired
    private EnderecoService service;

    @GetMapping("/listar")
    public ResponseEntity<List<EnderecoDTO>> listaEnderecos() {
        List<EnderecoDTO> enderecos = service.findAll();
        log.warn("Buscando enderecos...");
        if (enderecos.isEmpty()) {
            log.error("Não há endereços cadastrados.");
            return ResponseEntity.notFound().build();
        }
        log.info("Enderecos encontrados!");
        return ResponseEntity.ok(enderecos);
    }

    @GetMapping("/buscar/{id}")
    public ResponseEntity<EnderecoDTO> buscaEnderecoId(@PathVariable("id") Long id) {
        Optional<EnderecoDTO> endereco = service.findById(id);
        log.info("Buscando endereco...");
        if (endereco.isEmpty()) {
            log.error("Não há esse endereco cadastrado de id {}", id);
            return ResponseEntity.notFound().build();
        }
        log.info("Endereco encontrado!");
        return ResponseEntity.ok(endereco.get());
    }

    @GetMapping("/buscarEnderecos")
    public ResponseEntity<List<EnderecoDTO>> buscarEnderecosPorIds(@RequestParam List<Long> ids) {
        List<EnderecoDTO> enderecos = service.findAllById(ids);
        if (!enderecos.isEmpty()) {
            return ResponseEntity.ok(enderecos);
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping("/cadastrar")
    public ResponseEntity<EnderecoDTO> cadastrarEndereco(@RequestBody EnderecoDTO cadastroEndereco) {
        Optional<EnderecoDTO> enderecoDTO = service.save(cadastroEndereco);
        if (enderecoDTO.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        log.info("Endereco cadastrado!");
        return ResponseEntity.status(HttpStatus.CREATED).body(enderecoDTO.get());

    }

    @PostMapping("/cadastrarEnderecos")
    public ResponseEntity cadastrarEnderecos(@RequestBody List<EnderecoDTO> cadastroEnderecos) {
        cadastroEnderecos.forEach(enderecoDTO -> service.save(enderecoDTO));
        log.info("Enderecos cadastrados!");
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
