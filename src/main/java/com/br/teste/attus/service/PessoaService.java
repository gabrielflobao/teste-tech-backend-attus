package com.br.teste.attus.service;

import com.br.teste.attus.exceptions.pessoa.PessoaNotFoundException;
import com.br.teste.attus.mapper.PessoaMapper;
import com.br.teste.attus.repository.PessoaRepository;
import com.br.teste.attus.dto.PessoaDTO;
import com.br.teste.attus.entity.Pessoa;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Author : Gabriel F F Lobão
 */
@Service
public class PessoaService {

    @Autowired
    private PessoaRepository repository;

    public PessoaDTO save(PessoaDTO pessoa) {
        Pessoa pessoas = PessoaMapper.toRequest(pessoa);
        return PessoaMapper.toReponse(repository.save(pessoas));
    }

    public List<PessoaDTO> saveLista(List<PessoaDTO>  pessoa) {
        return PessoaMapper.toReponseList(repository.saveAll(PessoaMapper.toRequestListSave(pessoa)));
    }

    public List<PessoaDTO> findAll() {
        List<Pessoa> list = repository.findAll();
        if(list.isEmpty()){
            throw new PessoaNotFoundException("Nenhuma pessoa encontrada!",
                    "Não há pessoas cadastradas no sistema");
        }
        return PessoaMapper.toReponseList(list);
    }

    public PessoaDTO findById(Long id) {
        Optional<Pessoa> entity = repository.findById(id);
        if (entity.isEmpty()) {
            throw new PessoaNotFoundException("Nenhuma pessoa encontrada!",
                    "Não consta nenhuma pessoa com o ID informado");
        }
        return PessoaMapper.toReponse(entity.get());

    }

    public List<PessoaDTO> findAllById(List<Long> id) {
        List<Pessoa> entitys = repository.findAllById(id);
        if(entitys.isEmpty()){
            throw new PessoaNotFoundException("Nenhuma pessoa encontrada!",
                    "Não esta pessoa cadastrada");
        }
        return PessoaMapper.toReponseList(entitys);
    }

    public boolean existsPessoaById(Long id) {
        return repository.existsById(id);

    }

}
