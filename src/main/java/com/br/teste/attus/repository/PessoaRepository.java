package com.br.teste.attus.repository;

import com.br.teste.attus.entity.Pessoa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
/**
 * Author : Gabriel F F Lobão
 */
@Repository

public interface PessoaRepository extends JpaRepository<Pessoa,Long> {
}
