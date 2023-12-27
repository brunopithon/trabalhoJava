package br.edu.ifrn.gerencia.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import br.edu.ifrn.gerencia.domain.hospede.Hospede;
import br.edu.ifrn.gerencia.domain.reserva.Reserva;
import br.edu.ifrn.gerencia.repository.HospedeRepository;
import br.edu.ifrn.gerencia.repository.ReservaRepository;
import jakarta.validation.Valid;

@RestController
@RequestMapping("reserva")
public class ReservaController {

    @Autowired
    private ReservaRepository repository;

    @Autowired
    private ReservaRepository reservaRepository;

    @Autowired
    private HospedeRepository hospedeRepository;

    @PostMapping
    @Transactional
    public ResponseEntity<Object> cadastrar(@RequestBody @Valid Reserva reserva,
        UriComponentsBuilder uriBuilder) {

        Long idHospede = reserva.getHospede().getId();
        Hospede hospede = hospedeRepository.findById(idHospede).orElse(null);

        if (hospede == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body("Hóspede não encontrado.");
        }

        // Verifica se o hóspede atingiu o limite de 3 reservas
        long numeroReservasHospede = reservaRepository.countByHospedeId(idHospede);

        if (numeroReservasHospede >= 3) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body("O hóspede já possui 3 reservas e não pode criar mais.");
        }

        Reserva reservaLocal = reservaRepository.save(reserva);
        var uri = uriBuilder.path("/reserva/{id}").buildAndExpand(reservaLocal.getId()).toUri();
        return ResponseEntity.created(uri).build();
    }


    @GetMapping("/{id}")
    public ResponseEntity<Reserva> detalhar(@PathVariable Long id) {
        Reserva reserva = repository.getReferenceById(id);
        return ResponseEntity.ok(reserva);
    }

    @GetMapping
    public ResponseEntity<Page<Reserva>> listar(@PageableDefault(size = 30, sort = { "id" }) Pageable paginacao) {
        var reservas = repository.findAll(paginacao);
        return ResponseEntity.ok(reservas);
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<Object> excluir(@PathVariable Long id) {
        var reserva = repository.getReferenceById(id);
        repository.delete(reserva);
        return ResponseEntity.noContent().build();
    }

}
