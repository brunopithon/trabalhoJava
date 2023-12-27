package br.edu.ifrn.gerencia.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.edu.ifrn.gerencia.domain.acomodacao.Acomodacao;

public interface AcomodacaoRepository extends JpaRepository<Acomodacao, Long>{
    
}