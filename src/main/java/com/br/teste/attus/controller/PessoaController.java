package com.br.teste.attus.controller;

import com.br.teste.attus.dto.EnderecoDTO;
import com.br.teste.attus.dto.PessoaDTO;
import com.br.teste.attus.service.EnderecoService;
import com.br.teste.attus.service.PessoaService;
import jakarta.persistence.PrePersist;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Author : Gabriel F F Lobão
 */
@RestController
@RequestMapping("/pessoa")
public class PessoaController {
    @Autowired
    private PessoaService pessoaService;
    @Autowired
    private EnderecoService enderecoService;

    @GetMapping("/listar")
    public ResponseEntity<List<PessoaDTO>> listarPessoas() {
        List<PessoaDTO> pessoas = pessoaService.findAll();
        return ResponseEntity.ok(pessoas);
    }

    @GetMapping("/buscar/id")
    public ResponseEntity<List<PessoaDTO>> buscarPessoasPorId(@RequestParam List<Long> ids) {
        List<PessoaDTO> pessoas = pessoaService.findAllById(ids);
        return ResponseEntity.ok(pessoas);
    }
    @PrePersist
    @PostMapping("/cadastrar")
    public ResponseEntity<List<PessoaDTO>> cadastrarPessoas(@RequestBody List<PessoaDTO> pessoasEnderecos) {
         List<PessoaDTO> response = pessoaService.saveLista(pessoasEnderecos);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping(value = "/remove/principal/{id}")
    public ResponseEntity<EnderecoDTO> removeEnderecoPrincipal(@PathVariable("id") Long id) {
        return ResponseEntity.ok(enderecoService.removeEnderecoPrincipal(id));

    }

}
