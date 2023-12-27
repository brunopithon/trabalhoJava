package br.edu.ifrn.gerencia.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.edu.ifrn.gerencia.domain.hospede.Hospede;

public interface HospedeRepository extends JpaRepository<Hospede, Long>{
    
}
