package com.br.teste.attus.repository;

import com.br.teste.attus.dto.EnderecoDTO;
import com.br.teste.attus.entity.Endereco;
import com.br.teste.attus.entity.Pessoa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
/**
 * Author : Gabriel F F Lobão
 */
@Repository
public interface EnderecoRepository extends JpaRepository<Endereco,Long> {

    List<Endereco> findByPessoaId(Long id);

}
