package com.br.teste.attus.service;

import com.br.teste.attus.dto.EnderecoDTO;
import com.br.teste.attus.enuns.TipoPrincipal;
import com.br.teste.attus.exceptions.endereco.EnderecoExistenteException;
import com.br.teste.attus.exceptions.endereco.EnderecoNotFoundException;
import com.br.teste.attus.exceptions.endereco.EnderecoPrincipalExisteException;
import com.br.teste.attus.exceptions.endereco.EnderecoPrincipalInexistente;
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

    public EnderecoDTO save(EnderecoDTO endereco) {
        Endereco entity = EnderecoMapper.toRequest(endereco);
        return EnderecoMapper.toReponse(repository.save(entity));

    }

    public List<EnderecoDTO> saveAll(List<EnderecoDTO> endereco) {
        List<Endereco> enderecosSalvar = new ArrayList<Endereco>();
        endereco.forEach( enderecoDTO -> {
            boolean existePrincipal = repository.existsEnderecoByTpPrincipalSim(enderecoDTO.getPessoa().getId());
            if(existePrincipal && enderecoDTO.getTpPrincipal().equals(TipoPrincipal.S)) {
                throw new EnderecoPrincipalExisteException("Já existe endereço principal","Existe endereço principal para esta pessoa :"
                +enderecoDTO.getPessoa().getNomeCompleto()+"\n"+"id:"+enderecoDTO.getPessoa().getId());
            }
            enderecosSalvar.add(EnderecoMapper.toRequest(enderecoDTO));
        });

        if(enderecosSalvar.isEmpty()){
            throw new EnderecoNotFoundException("Não há endereços para salvar","Não existem endereços para salvar");
        }

        return EnderecoMapper.toReponseList(repository.saveAll(enderecosSalvar));

    }



    public EnderecoDTO update(EnderecoDTO endereco) {
        Endereco entity = EnderecoMapper.toRequest(endereco);
        return EnderecoMapper.toReponse(repository.save(entity));
    }

    public List<EnderecoDTO> findAll() {
        List<Endereco> list = repository.findAll();
        if(list.isEmpty()){
            throw new EnderecoNotFoundException("Não existe endereços","Não existe endereços cadastrados");
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
        if(entitys.isEmpty()){
            throw new EnderecoNotFoundException("Endereço não encontrado",
                    "Endereço referente ao id mencionado não foi encontrado");
        }
        return EnderecoMapper.toReponseList(entitys);
    }

    public List<EnderecoDTO> findByPessoaId(Long id) {
        return EnderecoMapper.toReponseList(repository.findByPessoaId(id));
    }

    public EnderecoDTO defineEnderecoPrincipal(Long id) {
        Boolean existe = repository.existePrincipalEndereco(id);
        if (existe) {
            throw new EnderecoPrincipalExisteException("Endereço principal existente", "Endereço definido como principal já existe");
        }
        Endereco endereco = repository.findById(id).get();
        endereco.setTpPrincipal(TipoPrincipal.S);
        return EnderecoMapper.toReponse(repository.save(endereco));

    }

    public EnderecoDTO removeEnderecoPrincipal(Long id) {
        Optional<Endereco> endereco = repository.findPrincipalEndereco(id,TipoPrincipal.S);
        if (endereco.isEmpty()) {
            throw new EnderecoPrincipalInexistente("Não existe endereço principal", "Não existe endereço principal");
        }
        endereco.get().setTpPrincipal(TipoPrincipal.N);
       return EnderecoMapper.toReponse(repository.save(endereco.get()));
    }
    public List<EnderecoDTO> buscarEnderecoPorPessoa(Long id) {
        List<Endereco> endereco = repository.findEnderecoByPessoaId(id);
        if (endereco.isEmpty()) {
            throw new EnderecoPrincipalInexistente("Não existe endereço principal", "Não existe endereço principal");
        }

        return EnderecoMapper.toReponseList(endereco);
    }

}
