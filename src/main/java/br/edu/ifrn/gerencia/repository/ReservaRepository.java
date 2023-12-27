package br.edu.ifrn.gerencia.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.edu.ifrn.gerencia.domain.reserva.Reserva;

public interface ReservaRepository extends JpaRepository<Reserva, Long>{
    
}