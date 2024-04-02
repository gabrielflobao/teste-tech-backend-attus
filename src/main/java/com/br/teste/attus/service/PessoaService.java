package com.br.teste.attus.service;

import com.br.teste.attus.dto.EnderecoDTO;
import com.br.teste.attus.entity.Endereco;
import com.br.teste.attus.exceptions.pessoa.PessoaNotFoundException;
import com.br.teste.attus.exceptions.utils.ExceptionUtils;
import com.br.teste.attus.mapper.EnderecoMapper;
import com.br.teste.attus.mapper.PessoaMapper;
import com.br.teste.attus.repository.PessoaRepository;
import com.br.teste.attus.dto.PessoaDTO;
import com.br.teste.attus.entity.Pessoa;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

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
        ExceptionUtils.checkListEmptyExceptionWithMsg(list,new PessoaNotFoundException("Nenhuma pessoa encontrada!",
                "Não há pessoas cadastradas no sistema"));
        return PessoaMapper.toReponseList(list);
    }

    public PessoaDTO findById(Long id) {
       Pessoa entity = repository.findById(id)
               .orElseThrow(()->  new PessoaNotFoundException("Nenhuma pessoa encontrada!",
                "Não consta nenhuma pessoa com o ID informado"));
        return PessoaMapper.toReponse(entity);

    }

    public List<PessoaDTO> findAllById(List<Long> id) {
        List<Pessoa> entitys = repository.findAllById(id);
        ExceptionUtils.checkListEmptyExceptionWithMsg(entitys, new PessoaNotFoundException("Nenhuma pessoa encontrada!",
                "Não há pessoas cadastradas no sistema"));
        return PessoaMapper.toReponseList(entitys);
    }

    public boolean existsPessoaById(Long id) {
        return repository.existsById(id);

    }

    public List<PessoaDTO> updatePessoas(List<PessoaDTO> pessoaDTOS) {
        List<Long> idsDasPessoasDTOS = extrairIdsDasPessoas(pessoaDTOS);
        List<Pessoa> pessoasDoBanco = buscarPessoasPorIds(idsDasPessoasDTOS);
        List<Pessoa> pessoasAtualizadas = atualizarPessoas(pessoasDoBanco, pessoaDTOS);
        savePessoasNoBanco(pessoasAtualizadas);
        ExceptionUtils.checkListEmptyExceptionWithMsg(pessoasAtualizadas, new PessoaNotFoundException("Nenhuma pessoa encontrada!",
                "Não foram encontradas as pessoas para atualização"));
        return mapearPesssoasParaDTO(pessoasAtualizadas);
    }

    private List<PessoaDTO> mapearPesssoasParaDTO(List<Pessoa> pessoasAtualizadas) {
        return PessoaMapper.toReponseList(pessoasAtualizadas);
    }

    private List<Long> extrairIdsDasPessoas(List<PessoaDTO> pessoaDTOS) {
        return pessoaDTOS.stream()
                .map(PessoaDTO::getId)
                .collect(Collectors.toList());

    }

    private List<Pessoa> buscarPessoasPorIds(List<Long> pessoasIds) {
        return repository.findAllById(pessoasIds);
    }

    private List<Pessoa> atualizarPessoas(List<Pessoa> pessoasBanco, List<PessoaDTO> pessoaDTOS) {
        return pessoasBanco.stream()
                .peek(pessoaBanco -> atualizaPessoa(pessoaBanco, pessoaDTOS))
                .collect(Collectors.toList());
    }

    private void savePessoasNoBanco(List<Pessoa> pessoas) {
        repository.saveAll(pessoas);
    }

    private void atualizaPessoa(Pessoa pessoaBanco, List<PessoaDTO> pessoaDTOS) {
        pessoaDTOS.stream()
                .filter(pessoaDTO -> Objects.equals(pessoaDTO.getId(), pessoaBanco.getId()))
                .findFirst()
                .ifPresent(pessoaDTO -> {
                    pessoaBanco.setNomeCompleto(Objects.requireNonNullElse(pessoaDTO.getNomeCompleto(), pessoaDTO.getNomeCompleto()));
                    pessoaBanco.setDataNascimento(Objects.requireNonNullElse(pessoaDTO.getDataNascimento(), pessoaDTO.getDataNascimento()));

                });
    }

    private List<EnderecoDTO> mapearEnderecosParaDTO(List<Endereco> enderecos) {
        return EnderecoMapper.toReponseList(enderecos);
    }
}