package com.br.teste.attus.service;

import com.br.teste.attus.dto.EnderecoDTO;
import com.br.teste.attus.enuns.TipoPrincipal;
import com.br.teste.attus.exceptions.endereco.EnderecoExistenteException;
import com.br.teste.attus.exceptions.endereco.EnderecoNotFoundException;
import com.br.teste.attus.exceptions.endereco.EnderecoPrincipalFoundException;
import com.br.teste.attus.exceptions.endereco.EnderecoPrincipalNotFound;
import com.br.teste.attus.exceptions.pessoa.PessoaNotFoundException;
import com.br.teste.attus.mapper.EnderecoMapper;
import com.br.teste.attus.entity.Endereco;
import com.br.teste.attus.repository.EnderecoRepository;
import com.br.teste.attus.repository.PessoaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Author : Gabriel F F Lobão
 */
@Service
public class EnderecoService {

    @Autowired
    private EnderecoRepository repository;

    @Autowired
    PessoaRepository pessoaRepository;

    @Autowired
    PessoaService pessoaService;

    public List<EnderecoDTO> saveAllByIdPessoa(Long idPessoa, List<EnderecoDTO> endereco) {
        boolean existePessoa = pessoaService.existsPessoaById(idPessoa);
        List<EnderecoDTO> response = new ArrayList<EnderecoDTO>();
        if (existePessoa) {
            List<EnderecoDTO> enderecosDTO = new ArrayList<>();
            endereco.forEach(enderecoDTO -> {
                enderecoDTO.setPessoa(pessoaRepository.findById(idPessoa).get());
                enderecosDTO.add(enderecoDTO);
            });
            response = saveAll(enderecosDTO);
        } else {
            throw new PessoaNotFoundException("ID pessoa não existe", "Não existe pessoa conforme o ID");
        }

        return response;
    }

    public List<EnderecoDTO> saveAll(List<EnderecoDTO> endereco) {
        List<Endereco> enderecosSalvar = new ArrayList<Endereco>();
        endereco.forEach(enderecoDTO -> {
            Optional<Endereco> existePrincipal = repository.findEnderecoByTpPrincipalSim(enderecoDTO.getPessoa().getId());
            if (existePrincipal.isPresent() && enderecoDTO.getTpPrincipal().equals(TipoPrincipal.S)) {
                throw new EnderecoPrincipalFoundException("Já existe endereço principal", "Existe endereço principal para esta pessoa :"
                        + enderecoDTO.getPessoa().getNomeCompleto() + "id:" + enderecoDTO.getPessoa().getId());
            }
            enderecosSalvar.add(EnderecoMapper.toRequest(enderecoDTO));
        });

        if (enderecosSalvar.isEmpty()) {
            throw new EnderecoNotFoundException("Não há endereços para salvar", "Não existem endereços para salvar");
        }

        return EnderecoMapper.toReponseList(repository.saveAll(enderecosSalvar));

    }

    public EnderecoDTO update(EnderecoDTO endereco) {
        Endereco entity = EnderecoMapper.toRequest(endereco);
        return EnderecoMapper.toReponse(repository.save(entity));
    }

    public List<EnderecoDTO> findAll() {
        List<Endereco> list = repository.findAll();
        if (list.isEmpty()) {
            throw new EnderecoNotFoundException("Não existe endereços", "Não existe endereços cadastrados");
        }
        return EnderecoMapper.toReponseList(list);
    }

    public EnderecoDTO findById(Long id) {
        Optional<Endereco> entity = repository.findById(id);
        if (entity.isEmpty()) {
            throw new EnderecoExistenteException("Endereço não existe", "Código do endereço informado não existe");
        }
        return EnderecoMapper.toReponse(entity.get());
    }

    public List<EnderecoDTO> findAllById(List<Long> id) {
        List<Endereco> entitys = repository.findAllById(id);
        if (entitys.isEmpty()) {
            throw new EnderecoNotFoundException("Endereço não encontrado",
                    "Endereço referente ao id mencionado não foi encontrado");
        }
        return EnderecoMapper.toReponseList(entitys);
    }

    public List<EnderecoDTO> findByPessoaId(Long id) {
        return EnderecoMapper.toReponseList(repository.findByPessoaId(id));
    }

    public EnderecoDTO defineEnderecoPrincipal(Long id) {
        if (repository.existsById(id)) {
            boolean existePrincipal = repository.existePrincipalEndereco(id);
            if (existePrincipal) {
                throw new EnderecoPrincipalFoundException("Endereço principal existente", "Endereço definido como principal já existe");
            }
        } else {
            throw new EnderecoNotFoundException("Não existe endereço para o ID informado", "Não existe endereço conforme o ID informado");
        }

        Endereco endereco = repository.findById(id).get();
        endereco.setTpPrincipal(TipoPrincipal.S);
        return EnderecoMapper.toReponse(repository.save(endereco));

    }

    public EnderecoDTO removeEnderecoPrincipal(Long id) {
        Optional<Endereco> endereco = repository.findPrincipalEndereco(id, TipoPrincipal.S);
        if (endereco.isEmpty()) {
            throw new EnderecoPrincipalNotFound("Não existe endereço principal", "Não existe endereço principal no ID pessoa informado");
        }
        endereco.get().setTpPrincipal(TipoPrincipal.N);
        return EnderecoMapper.toReponse(repository.save(endereco.get()));
    }

    public List<EnderecoDTO> buscarEnderecoPorPessoa(Long id) {
        List<Endereco> endereco = repository.findEnderecoByPessoaId(id);
        if (endereco.isEmpty()) {
            throw new EnderecoPrincipalNotFound("Não existe endereço", "Não existe endereço para o ID pessoa informado");
        }

        return EnderecoMapper.toReponseList(endereco);
    }


}
