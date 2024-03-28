package com.br.teste.attus.service;

import com.br.teste.attus.dto.PessoaEnderecoDTO;
import com.br.teste.attus.exceptions.PessoaExistenteException;
import com.br.teste.attus.mapper.PessoaEnderecoMapper;
import com.br.teste.attus.mapper.PessoaMapper;
import com.br.teste.attus.repository.PessoaRepository;
import com.br.teste.attus.dto.PessoaDTO;
import com.br.teste.attus.entity.Pessoa;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Author : Gabriel F F Lobão
 */
@Service
public class PessoaService {

    @Autowired
    private PessoaRepository repository;

    public Optional<PessoaDTO> save(Pessoa pessoa) {
        if (repository.existsById(pessoa.getId())) {
            throw new PessoaExistenteException("Pessoa já existe!",
                    "Pessoa com este id :" + pessoa.getId() + " já está cadastrada"
            );
        }
        return Optional.of(PessoaMapper.toReponse(repository.save(pessoa)));
    }
    public PessoaDTO save(PessoaEnderecoDTO pessoa) {
        if (repository.existsById(pessoa.getId())) {
            throw new PessoaExistenteException("Pessoa já existe!",
                    "Pessoa com este id :" + pessoa.getId() + " já está cadastrada"
            );
        }
        return PessoaMapper.toReponse(repository.save(PessoaMapper.toRequest(pessoa)));
    }

    public List<PessoaDTO> findAll() {
        List<Pessoa> list = repository.findAll();
        return PessoaMapper.toReponseList(list);
    }

    public PessoaDTO findById(Long id) {
        Optional<Pessoa> entity = repository.findById(id);
        return entity.map(PessoaMapper::toReponse).orElse(null);

    }

    public List<PessoaDTO> findAllById(List<Long> id) {
        List<Pessoa> entitys = repository.findAllById(id);
        return PessoaMapper.toReponseList(entitys);
    }

    public boolean existsPessoaById(Long id) {

        return repository.existsById(id);

    }

}
