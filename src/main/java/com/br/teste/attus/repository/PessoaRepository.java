package com.br.teste.attus.repository;

import com.br.teste.attus.entity.Pessoa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;
/**
 * Author : Gabriel F F Lobão
 */
@Repository

public interface PessoaRepository extends JpaRepository<Pessoa,Long> {
}
