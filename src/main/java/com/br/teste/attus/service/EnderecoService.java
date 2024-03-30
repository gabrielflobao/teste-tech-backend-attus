package com.br.teste.attus.service;

import com.br.teste.attus.dto.EnderecoDTO;
import com.br.teste.attus.entity.Pessoa;
import com.br.teste.attus.enuns.TipoPrincipal;
import com.br.teste.attus.exceptions.endereco.EnderecoExistenteException;
import com.br.teste.attus.exceptions.endereco.EnderecoNotFoundException;
import com.br.teste.attus.exceptions.endereco.EnderecoPrincipalFoundException;
import com.br.teste.attus.exceptions.endereco.EnderecoPrincipalNotFound;
import com.br.teste.attus.exceptions.pessoa.PessoaNotFoundException;
import com.br.teste.attus.exceptions.utils.ExceptionUtils;
import com.br.teste.attus.mapper.EnderecoMapper;
import com.br.teste.attus.entity.Endereco;
import com.br.teste.attus.repository.EnderecoRepository;
import com.br.teste.attus.repository.PessoaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
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

    public List<EnderecoDTO> saveAllByIdPessoa(Long idPessoa, List<EnderecoDTO> endereco) {
       return getResponseSaveEnderecosByIdPessoa(idPessoa,endereco);

    }

    public List<EnderecoDTO> saveAll(List<EnderecoDTO> endereco) {
        List<Endereco> enderecosSalvar = new ArrayList<Endereco>();
        endereco.forEach(enderecoDTO -> {
            validateExistingPrincipal(enderecoDTO, repository);
            enderecosSalvar.add(EnderecoMapper.toRequest(enderecoDTO));
        });
        ExceptionUtils.checkListEmptyExceptionWithMsg(enderecosSalvar, new EnderecoNotFoundException("Não há endereços para salvar",
                "Não existem endereços para salvar"));
        return EnderecoMapper.toReponseList(repository.saveAll(enderecosSalvar));

    }

    public EnderecoDTO update(EnderecoDTO endereco) {
        Endereco entity = EnderecoMapper.toRequest(endereco);
        return EnderecoMapper.toReponse(repository.save(entity));
    }

    public List<EnderecoDTO> findAll() {
        List<Endereco> list = repository.findAll();
        ExceptionUtils.checkListEmptyExceptionWithMsg(list, new EnderecoNotFoundException("Não existe endereços",
                "Não existe endereços cadastrados"));
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
        ExceptionUtils.checkListEmptyExceptionWithMsg(entitys, new EnderecoNotFoundException("Endereço não encontrado",
                "Endereço referente ao id mencionado não foi encontrado"));
        return EnderecoMapper.toReponseList(entitys);
    }

    public List<EnderecoDTO> findByPessoaId(Long id) {
        List<Endereco> lista = repository.findByPessoaId(id);
        ExceptionUtils.checkListEmptyExceptionWithMsg(lista, new EnderecoNotFoundException("Endereço não encontrado",
                "Endereço não encontrado referente ao ID pessoa informado"));
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

    public EnderecoDTO updateEnderecoPrincipalToN(Long id) {
        Optional<Endereco> endereco = repository.findPrincipalEndereco(id, TipoPrincipal.S);
        ExceptionUtils.checkOptionalEmptyExceptionWithMsg(endereco, new EnderecoPrincipalNotFound(
                "Não existe endereço principal",
                "Não existe endereço principal no ID pessoa informado"
        ));
        endereco.get().setTpPrincipal(TipoPrincipal.N);
        return EnderecoMapper.toReponse(repository.save(endereco.get()));
    }

    public List<EnderecoDTO> buscarEnderecoPorPessoa(Long id) {
        List<Endereco> endereco = repository.findEnderecoByPessoaId(id);
        ExceptionUtils.checkListEmptyExceptionWithMsg(endereco, new EnderecoPrincipalNotFound(
                "Não existe endereço",
                "Não existe endereço para o ID pessoa informado")
        );

        return EnderecoMapper.toReponseList(endereco);
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
        return repository.findAllById(enderecosIds);
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
        return EnderecoMapper.toReponseList(enderecos);
    }

    private void validateExistingPrincipal(EnderecoDTO addressDTO, EnderecoRepository repository) {
        Optional<Endereco> existingPrincipal = repository.findEnderecoByTpPrincipalSim(addressDTO.getPessoa().getId());
        if (existingPrincipal.isPresent() && addressDTO.getTpPrincipal().equals(TipoPrincipal.S)) {
            String errorMessage = "Existe endereço principal para esta pessoa :" + addressDTO.getPessoa().getNomeCompleto() + "id:" + addressDTO.getPessoa().getId();
            throw new EnderecoPrincipalFoundException("Já existe endereço principal", errorMessage);
        }
    }
    public List<EnderecoDTO> getResponseSaveEnderecosByIdPessoa (Long idPessoa,List<EnderecoDTO> endereco) {
        List<EnderecoDTO> response = new ArrayList<EnderecoDTO>();
        if (pessoaService.existsPessoaById(idPessoa)) {
            Pessoa pessoa = pessoaRepository.findById(idPessoa).get();
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


}