package com.br.teste.attus.controller;

import com.br.teste.attus.dto.EnderecoDTO;
import com.br.teste.attus.dto.EnderecoSaveDTO;
import com.br.teste.attus.service.EnderecoService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.websocket.server.PathParam;
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

    @Operation(summary = "Realiza a listagem de endereço(s).", method = "GET")
    @GetMapping("/listar")
    public ResponseEntity<List<EnderecoDTO>> listaEnderecos() {
        List<EnderecoDTO> enderecos = service.findAll();
        return ResponseEntity.ok(enderecos);
    }
    @Operation(summary = "Busca endereço pelo ID pessoa.", method = "GET")
    @GetMapping("/listar/pessoa/{id}")
    public ResponseEntity<List<EnderecoDTO>> findEnderecosPorPessoa(@PathVariable("id") Long id) {
        List<EnderecoDTO> endereco = service.findByPessoaId(id);
        return ResponseEntity.ok(endereco);
    }

    @Operation(summary = "Busca endereços pelos ID(s)" +
            ".", method = "GET")
    @GetMapping("/listar/")
    public ResponseEntity<List<EnderecoDTO>> buscarEnderecosPorIds(@RequestParam (name = "ids") List<Long> ids) {
        List<EnderecoDTO> enderecos = service.findAllById(ids);
        return ResponseEntity.ok(enderecos);
    }

    @Operation(summary = "Cadastrar endereços por meio do ID pessoa" +
            ".", method = "PUT")
    @PutMapping("/cadastrar/pessoa/{id}")
    public ResponseEntity<List<EnderecoDTO>> cadastrarEnderecos(@PathVariable("id") Long id, @RequestBody List<EnderecoSaveDTO> cadastroEnderecos) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.saveAllByIdPessoa(id, cadastroEnderecos));
    }

    @Operation(summary = "Define endereço principal" +
            ".", method = "PUT")
    @PutMapping(value = "/principal/{id}")
    public ResponseEntity<EnderecoDTO> definirEnderecoPrincipal(@PathVariable("id") Long id) {
        return ResponseEntity.ok(service.defineEnderecoPrincipal(id));

    }

    @Operation(summary = "Altera endereco principal Pelo ID" +
            ".", method = "Patch")
    @PutMapping(value = "/update/principal/")
    public ResponseEntity<EnderecoDTO> updateEnderecoPrincipalToN(@PathParam("id") Long id) {
        return ResponseEntity.ok(service.updateEnderecoPrincipalToN(id));

    }

    @Operation(summary = "Altera Endereços" +
            ".", method = "PUT")
    @PatchMapping(value = "/update")
    public ResponseEntity<List<EnderecoDTO>> updateEnderecos(@RequestBody List<EnderecoDTO> lista) {
        return ResponseEntity.ok(service.updateEnderecos(lista));

    }


}
