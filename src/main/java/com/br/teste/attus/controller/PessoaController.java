package com.br.teste.attus.controller;

import com.br.teste.attus.dto.EnderecoDTO;
import com.br.teste.attus.dto.PessoaDTO;
import com.br.teste.attus.dto.PessoaEnderecoDTO;
import com.br.teste.attus.entity.Pessoa;
import com.br.teste.attus.service.EnderecoService;
import com.br.teste.attus.service.PessoaService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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

    @GetMapping("/buscar/{id}")
    public ResponseEntity<PessoaDTO> buscaPessoaId(@PathVariable("id") Long id) {
        return ResponseEntity.ok(pessoaService.findById(id));
    }

    @GetMapping("/buscar/ids")
    public ResponseEntity<List<PessoaDTO>> buscarEnderecosPorIds(@RequestParam List<Long> ids) {
        List<PessoaDTO> pessoas = pessoaService.findAllById(ids);
        return ResponseEntity.ok(pessoas);
    }

    @PostMapping("/cadastrar")
    public ResponseEntity<PessoaDTO> cadastrarPessoa(@RequestBody Pessoa cadastroPessoa) {
        PessoaDTO response = pessoaService.save(cadastroPessoa);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping("/cadastrarPessoas")
    public ResponseEntity<List<PessoaDTO>> cadastrarEnderecos(@RequestBody List<PessoaEnderecoDTO> pessoasEnderecos) {
        List<PessoaDTO> response = new ArrayList<>();
        pessoasEnderecos.forEach(pessoasDTO -> response.add(pessoaService.save(pessoasDTO)));
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping(value = "/remove/principal/{id}")
    public ResponseEntity<EnderecoDTO> removeEnderecoPrincipal(@PathVariable("id") Long id) {
        return ResponseEntity.ok(enderecoService.removeEnderecoPrincipal(id));

    }

}
