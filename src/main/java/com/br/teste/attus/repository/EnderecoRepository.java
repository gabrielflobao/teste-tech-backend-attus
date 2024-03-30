package com.br.teste.attus.repository;

import com.br.teste.attus.dto.EnderecoDTO;
import com.br.teste.attus.entity.Endereco;
import com.br.teste.attus.entity.Pessoa;
import com.br.teste.attus.enuns.TipoPrincipal;
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

    @Query("SELECT t FROM Endereco t where t.pessoa.id= :param  ")
    Optional<Endereco> existePrincipalEndereco(@Param("param") Long id);
    @Query("SELECT t FROM Endereco t where t.pessoa.id = :id and t.tpPrincipal = :sn")
    Optional<Endereco> findPrincipalEndereco(@Param("id") Long id,@Param("sn") TipoPrincipal sn);

    @Query("SELECT t FROM Endereco t where t.pessoa.id = :id and t.tpPrincipal = 'S' ")
    Optional<Endereco> findEnderecoByTpPrincipalSim(@Param("id") Long id);

    List<Endereco> findEnderecoByPessoaId(Long id);

}
