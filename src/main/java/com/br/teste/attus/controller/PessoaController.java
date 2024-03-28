package com.br.teste.attus.controller;

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
    private static final Logger log = LoggerFactory.getLogger(PessoaController.class);
    @Autowired
    private PessoaService pessoaService;
    @Autowired
    private EnderecoService enderecoService;

    @GetMapping("/listar")
    public ResponseEntity<List<PessoaDTO>> listarPessoas() {
        List<PessoaDTO> pessoas = pessoaService.findAll();
        log.info("Buscando pessoas...");
        if (pessoas.isEmpty()) {
            log.info("Não há pessoas cadastradas.");
            return ResponseEntity.notFound().build();
        }
        log.info("Pessoas encontrados!");
        return ResponseEntity.ok(pessoas);
    }

    @GetMapping("/buscar/{id}")
    public ResponseEntity<PessoaDTO> buscaPessoaId(@PathVariable("id") Long id) {
        if (!pessoaService.existsPessoaById(id)) {
            log.info("Não há  pessoa cadastrada com id {}", id);
            return ResponseEntity.notFound().build();
        }
        log.info("Pessoa encontrado!");
        return ResponseEntity.ok(pessoaService.findById(id));
    }

    @GetMapping("/buscarPessoas")
    public ResponseEntity<List<PessoaDTO>> buscarEnderecosPorIds(@RequestParam List<Long> ids) {
        List<PessoaDTO> pessoas = pessoaService.findAllById(ids);
        if (pessoas.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(pessoas);
    }

    @PostMapping("/cadastrar")
    public ResponseEntity<PessoaDTO> cadastrarPessoa(@RequestBody Pessoa cadastroPessoa) {
      Optional<PessoaDTO> response =  pessoaService.save(cadastroPessoa);
        if (response.isPresent()) {
            log.info("Pessoa cadastrada!");
            return ResponseEntity.status(HttpStatus.CREATED).body(response.get());
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping("/cadastrarPessoas")
    public ResponseEntity<List<PessoaDTO>> cadastrarEnderecos(@RequestBody List<PessoaEnderecoDTO> pessoasEnderecos) {
        List<PessoaDTO> response = new ArrayList<>();
        pessoasEnderecos.forEach(pessoasDTO -> response.add(pessoaService.save(pessoasDTO)));
        log.info("Pessoas cadastradas!");
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }


}
