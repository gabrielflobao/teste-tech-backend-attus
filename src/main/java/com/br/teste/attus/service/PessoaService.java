package com.br.teste.attus.service;

import com.br.teste.attus.dto.PessoaEnderecoDTO;
import com.br.teste.attus.exceptions.pessoa.PessoaExistenteException;
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

    public PessoaDTO save(Pessoa pessoa) {
        if (repository.existsById(pessoa.getId())) {
            throw new PessoaExistenteException("Pessoa já existe!",
                    "Pessoa com este id :" + pessoa.getId() + " já está cadastrada"
            );
        }

        return PessoaMapper.toReponse(repository.save(pessoa));
    }
    public PessoaDTO save(PessoaEnderecoDTO pessoa) {
        if (repository.existsById(pessoa.getId())) {
            throw new PessoaExistenteException("Pessoa já existe!",
                    "Pessoa com este id :" + pessoa.getId() + " já está cadastrada"
            );
        }
        return PessoaMapper.toReponse(repository.save(PessoaMapper.toRequest(pessoa)));
    }

    public List<PessoaDTO> saveLista(List<PessoaEnderecoDTO>  pessoa) {
        List<PessoaDTO> response = new ArrayList<>();
        pessoa.forEach(pessoasDTO -> {
            if (existsPessoaById(pessoasDTO.getId())) {
                throw new PessoaExistenteException("Pessoa já existe!",
                        "Pessoa com este id :" + pessoasDTO.getId() + " já está cadastrada"
                );
            }
            response.add(save(PessoaMapper.toRequest(pessoasDTO)));
        });
        return response;
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
                    "Não esta pessoa cadastrada");
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
