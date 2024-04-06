package com.br.teste.attus.service;

import com.br.teste.attus.dto.EnderecoDTO;
import com.br.teste.attus.dto.PessoaSaveDTO;
import com.br.teste.attus.entity.Endereco;
import com.br.teste.attus.enuns.TipoPrincipal;
import com.br.teste.attus.exceptions.endereco.EnderecoPrincipalFoundException;
import com.br.teste.attus.exceptions.pessoa.PessoaNotFoundException;
import com.br.teste.attus.exceptions.utils.ExceptionUtils;
import com.br.teste.attus.mapper.*;
import com.br.teste.attus.repository.PessoaRepository;
import com.br.teste.attus.dto.PessoaDTO;
import com.br.teste.attus.entity.Pessoa;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Author : Gabriel F F Lobão
 */
@Service
public class PessoaService {

    @Autowired
    private PessoaRepository repository;

    public PessoaSaveDTO save(PessoaSaveDTO pessoa) {
        Pessoa pessoas = PessoaSaveRequestMapper.toRequestSave(pessoa);
        return PessoaSaveResponseMapper.toResponse(repository.save(pessoas));
    }

    public List<PessoaSaveDTO> saveLista(List<PessoaSaveDTO> pessoa) {
        List<Pessoa> pessoaSave = PessoaSaveRequestMapper.toRequestSaveList(pessoa);
        if (verificaPessoaEnderecoPrincipal(pessoaSave)) {
            throw new EnderecoPrincipalFoundException("Existe mais de um endereço principal", "Existe mais de um endereço principal para a pessoa a ser cadastrada");
        }

        return PessoaSaveResponseMapper.toResponseList(repository.saveAll(pessoaSave));
    }

    private boolean verificaPessoaEnderecoPrincipal(List<Pessoa> pessoas) {
        boolean existe = false;
        for (Pessoa pessoa : pessoas) {
            if(pessoa.getEnderecos() !=null) {
                existe = verificaMaisDeUmEnderecoPrincipal(pessoa.getEnderecos());
            }
            if (existe) {
                return existe;
            }
        }

        return existe;

    }

    private boolean verificaMaisDeUmEnderecoPrincipal(List<Endereco> enderecos) {
        Integer qtdEndPrincipal = 0;
        for (Endereco endereco : enderecos) {
            if (endereco.getTpPrincipal().equals(TipoPrincipal.S)) {
                qtdEndPrincipal++;
            }
            if (qtdEndPrincipal > 1) {
                return true;
            }
        }
        return false;
    }

    public List<PessoaDTO> findAll() {
        List<Pessoa> list = repository.findAll();
        ExceptionUtils.checkListEmptyExceptionWithMsg(list, new PessoaNotFoundException("Nenhuma pessoa encontrada!",
                "Não há pessoas cadastradas no sistema"));
        return PessoaResponseMapper.toReponseList(list);
    }

    public PessoaDTO findById(Long id) {
        Pessoa entity = repository.findById(id)
                .orElseThrow(() -> new PessoaNotFoundException("Nenhuma pessoa encontrada!",
                        "Não consta nenhuma pessoa com o ID informado"));
        return PessoaResponseMapper.toReponse(entity);

    }

    public List<PessoaDTO> findAllById(List<Long> id) {
        List<Pessoa> entitys = repository.findAllById(id);
        ExceptionUtils.checkListEmptyExceptionWithMsg(entitys, new PessoaNotFoundException("Nenhuma pessoa encontrada!",
                "Não há pessoas cadastradas no sistema"));
        return PessoaResponseMapper.toReponseList(entitys);
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
        return PessoaResponseMapper.toReponseList(pessoasAtualizadas);
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
        return EnderecoResponseMapper.toReponseList(enderecos);
    }
}