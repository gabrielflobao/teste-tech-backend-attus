package com.br.teste.attus.service;

import com.br.teste.attus.dto.EnderecoDTO;
import com.br.teste.attus.dto.EnderecoSaveDTO;
import com.br.teste.attus.entity.Endereco;
import com.br.teste.attus.entity.Pessoa;
import com.br.teste.attus.enuns.TipoPrincipal;
import com.br.teste.attus.exceptions.endereco.EnderecoNotFoundException;
import com.br.teste.attus.exceptions.endereco.EnderecoPrincipalFoundException;
import com.br.teste.attus.exceptions.endereco.EnderecoPrincipalNotFound;
import com.br.teste.attus.exceptions.pessoa.PessoaNotFoundException;
import com.br.teste.attus.exceptions.utils.ExceptionUtils;
import com.br.teste.attus.mapper.EnderecoRequestMapper;
import com.br.teste.attus.mapper.EnderecoResponseMapper;
import com.br.teste.attus.mapper.EnderecoSaveMapper;
import com.br.teste.attus.repository.EnderecoRepository;
import com.br.teste.attus.repository.PessoaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

/**
 * Author : Gabriel F F Lobão
 */
@Service
public class EnderecoService {

    @Autowired
    private EnderecoRepository repository;

    @Autowired
    private PessoaRepository pessoaRepository;

    @Autowired
    private PessoaService pessoaService;

    public List<EnderecoDTO> saveAllByIdPessoa(Long idPessoa, List<EnderecoSaveDTO> endereco) {
        return getResponseSaveEnderecosByIdPessoa(idPessoa, endereco);

    }

    public List<EnderecoDTO> saveAll(List<EnderecoSaveDTO> endereco, Pessoa pessoa) {
        List<Endereco> enderecosSalvar = new ArrayList<Endereco>();
        endereco.forEach(enderecoDTO -> {
            validateExistingPrincipal(enderecoDTO, repository, pessoa);
            enderecosSalvar.add(EnderecoSaveMapper.toRequest(enderecoDTO, pessoa));
        });
        ExceptionUtils.checkListEmptyExceptionWithMsg(enderecosSalvar, new EnderecoNotFoundException("Não há endereços para salvar",
                "Não existem endereços para salvar"));
        return EnderecoResponseMapper.toReponseList(repository.saveAll(enderecosSalvar));

    }

    public EnderecoDTO update(EnderecoDTO endereco) {
        Endereco entity = EnderecoRequestMapper.toRequest(endereco);
        return EnderecoResponseMapper.toReponse(repository.save(entity));
    }

    public List<EnderecoDTO> findAll() {
        List<Endereco> list = repository.findAll();
        ExceptionUtils.checkListEmptyExceptionWithMsg(list, new EnderecoNotFoundException("Não existe endereços",
                "Não existe endereços cadastrados"));
        return EnderecoResponseMapper.toReponseList(list);
    }

    public EnderecoDTO findById(Long id) {
        Endereco entity = repository.findById(id)
                .orElseThrow(() -> new EnderecoNotFoundException("Endereço não existe", "Código do endereço informado não existe"));
        return EnderecoResponseMapper.toReponse(entity);
    }

    public List<EnderecoDTO> findAllById(List<Long> id) {
        List<Endereco> entitys = repository.findAllById(id);
        ExceptionUtils.checkListEmptyExceptionWithMsg(entitys, new EnderecoNotFoundException("Endereço não encontrado", "Endereço referente ao id mencionado não foi encontrado"));

        return EnderecoResponseMapper.toReponseList(entitys);
    }

    public List<EnderecoDTO> findByPessoaId(Long id) {
        List<Endereco> lista = repository.findByPessoaId(id);
        ExceptionUtils.checkListEmptyExceptionWithMsg(lista,
                new EnderecoNotFoundException("Endereço não encontrado",
                        "Endereço não encontrado referente ao ID pessoa informado"));

        return EnderecoResponseMapper.toReponseList(lista);
    }

    public EnderecoDTO defineEnderecoPrincipal(Long id) {
        AtomicReference<Endereco> enderecoRef = new AtomicReference<>();
        Optional<Endereco> opEndereco = repository.findById(id);

        opEndereco.ifPresentOrElse(
                endereco -> {
                    enderecoRef.set(endereco);
                    Optional<Endereco> opEnderecoPrincipal = repository.findEnderecoByTpPrincipalSim(id);
                    if (opEnderecoPrincipal.isPresent()) {
                        throw new EnderecoPrincipalFoundException("Endereço principal existente", "Endereço definido como principal já existe");
                    } else {
                        endereco.setTpPrincipal(TipoPrincipal.S);
                        repository.save(endereco);
                    }
                },
                () -> {
                    throw new EnderecoNotFoundException("Não existe endereço para o ID informado", "Não existe endereço conforme o ID informado");
                }
        );
        Endereco endereco = enderecoRef.get();
        return EnderecoResponseMapper.toReponse(endereco);
    }


    public EnderecoDTO updateEnderecoPrincipalToN(Long id) {
        Endereco endereco = repository.findPrincipalEndereco(id, TipoPrincipal.S).orElseThrow(() ->
                new EnderecoPrincipalNotFound(
                        "Não existe endereço principal",
                        "Não existe endereço principal no ID pessoa informado"));

        endereco.setTpPrincipal(TipoPrincipal.N);
        return EnderecoResponseMapper.toReponse(repository.save(endereco));
    }

    public List<EnderecoDTO> updateEnderecos(List<EnderecoDTO> enderecosDTO) {
        List<Long> idsDosEnderecosDTO = extrairIdsDosEnderecos(enderecosDTO);
        List<Endereco> enderecosDoBanco = buscarEnderecosPorIds(idsDosEnderecosDTO);
        List<Endereco> enderecosAtualizados = atualizarEnderecos(enderecosDoBanco, enderecosDTO);
        salvarEnderecosNoBanco(enderecosAtualizados);
        return mapearEnderecosParaDTO(enderecosAtualizados);
    }

    private List<Long> extrairIdsDosEnderecos(List<EnderecoDTO> enderecosDTO) {
        return enderecosDTO.stream()
                .map(EnderecoDTO::getId)
                .collect(Collectors.toList());
    }

    private List<Endereco> buscarEnderecosPorIds(List<Long> enderecosIds) {
        List<Endereco> enderecos = repository.findAllById(enderecosIds);
        ExceptionUtils.checkListEmptyExceptionWithMsg(enderecos, new EnderecoNotFoundException("Endereços não encontrados", "Endereços não foram encontrados"));
        return enderecos;
    }

    private List<Endereco> atualizarEnderecos(List<Endereco> enderecosDoBanco, List<EnderecoDTO> enderecosDTO) {
        return enderecosDoBanco.stream()
                .peek(enderecoDoBanco -> atualizarEndereco(enderecoDoBanco, enderecosDTO))
                .collect(Collectors.toList());
    }

    private void salvarEnderecosNoBanco(List<Endereco> enderecos) {
        repository.saveAll(enderecos);
    }

    protected void atualizarEndereco(Endereco enderecoDoBanco, List<EnderecoDTO> enderecosDTO) {
        enderecosDTO.stream()
                .filter(enderecoDTO -> Objects.equals(enderecoDTO.getId(), enderecoDoBanco.getId()))
                .findFirst()
                .ifPresent(enderecoDTO -> {
                    enderecoDoBanco.setLogradouro(Objects.requireNonNullElse(enderecoDTO.getLogradouro(), enderecoDoBanco.getLogradouro()));
                    enderecoDoBanco.setCep(Objects.requireNonNullElse(enderecoDTO.getCep(), enderecoDoBanco.getCep()));
                    enderecoDoBanco.setNumero(Objects.requireNonNullElse(enderecoDTO.getNumero(), enderecoDoBanco.getNumero()));
                    enderecoDoBanco.setCidade(Objects.requireNonNullElse(enderecoDTO.getCidade(), enderecoDoBanco.getCidade()));
                    enderecoDoBanco.setTpPrincipal(Objects.requireNonNullElse(enderecoDTO.getTpPrincipal(), enderecoDoBanco.getTpPrincipal()));
                    enderecoDoBanco.setEstado(Objects.requireNonNullElse(enderecoDTO.getEstado(), enderecoDoBanco.getEstado()));
                });
    }

    private List<EnderecoDTO> mapearEnderecosParaDTO(List<Endereco> enderecos) {
        return EnderecoResponseMapper.toReponseList(enderecos);
    }

    private void validateExistingPrincipal(EnderecoSaveDTO addressDTO, EnderecoRepository repository, Pessoa pessoa) {
        Optional<Endereco> existingPrincipal = repository.findEnderecoByTpPrincipalSim(pessoa.getId());
        if (existingPrincipal.isPresent() && addressDTO.getTpPrincipal().equals(TipoPrincipal.S)) {
            String errorMessage = "Existe endereço principal para esta pessoa :" + pessoa.getNomeCompleto() + "id:" + pessoa.getId();
            throw new EnderecoPrincipalFoundException("Já existe endereço principal", errorMessage);
        }
    }

    public List<EnderecoDTO> getResponseSaveEnderecosByIdPessoa(Long idPessoa, List<EnderecoSaveDTO> endereco) {
        List<EnderecoDTO> response = new ArrayList<EnderecoDTO>();
        if (pessoaService.existsPessoaById(idPessoa)) {
            Pessoa pessoa = pessoaRepository.findById(idPessoa).get();
            response = saveAll(endereco, pessoa);
        } else {
            throw new PessoaNotFoundException("ID pessoa não existe", "Não existe pessoa conforme o ID");
        }

        return response;
    }


}