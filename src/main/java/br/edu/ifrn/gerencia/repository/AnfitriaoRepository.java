package br.edu.ifrn.gerencia.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.edu.ifrn.gerencia.domain.anfitriao.Anfitriao;

public interface AnfitriaoRepository extends JpaRepository<Anfitriao, Long>{
    
}
