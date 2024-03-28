package com.br.teste.attus.service;

import com.br.teste.attus.dto.EnderecoDTO;
import com.br.teste.attus.entity.Pessoa;
import com.br.teste.attus.exceptions.EnderecoExistenteException;
import com.br.teste.attus.mapper.EnderecoMapper;
import com.br.teste.attus.entity.Endereco;
import com.br.teste.attus.repository.EnderecoRepository;
import com.br.teste.attus.repository.PessoaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    public Optional<EnderecoDTO> save(EnderecoDTO endereco) {
        if(repository.existsById(endereco.getId())){
            throw new EnderecoExistenteException("Endereco já existente!",
                    "Endereco com este id :" + endereco.getId() + " já está cadastrado"
            );
        }
        Endereco entity = EnderecoMapper.toRequest(endereco);
        return Optional.of(EnderecoMapper.toReponse(repository.save(entity)));

    }

    public List<EnderecoDTO> findAll() {
        List<Endereco> list = repository.findAll();
        return EnderecoMapper.toReponseList(list);
    }

    public Optional<EnderecoDTO> findById(Long id) {
        Optional<Endereco> entity = repository.findById(id);
        Optional<EnderecoDTO> empty = Optional.empty();
        return entity.map(EnderecoMapper::toReponse).or(() -> empty);
    }

    public List<EnderecoDTO>  findAllById(List <Long> id) {
        List<Endereco> entitys = repository.findAllById(id);
        return EnderecoMapper.toReponseList(entitys);
    }

    public boolean existsEnderecoById(Long id) {
        return repository.existsById(id);
    }

    public List<EnderecoDTO> findByPessoaId(Long id) {
        return EnderecoMapper.toReponseList(repository.findByPessoaId(id));
    }




}
