package br.edu.ifrn.gerencia.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import br.edu.ifrn.gerencia.domain.acomodacao.Acomodacao;
import br.edu.ifrn.gerencia.repository.AcomodacaoRepository;
import jakarta.validation.Valid;

@RestController
@RequestMapping("acomodacao")
public class AcomodacaoController {

    @Autowired
    private AcomodacaoRepository repository;

    @GetMapping("/{id}")
    public ResponseEntity<Acomodacao> detalhar(@PathVariable Long id) {
        Acomodacao acomodacao = repository.getReferenceById(id);
        return ResponseEntity.ok(acomodacao);
    }

    @GetMapping
    public ResponseEntity<Page<Acomodacao>> listar(@PageableDefault(size = 30, sort = { "nome" }) Pageable paginacao) {
        var acomodacoes = repository.findAll(paginacao);
        return ResponseEntity.ok(acomodacoes);
    }

    @PostMapping
    @Transactional
    public ResponseEntity<Object> cadastrar(@RequestBody @Valid Acomodacao acomodacao,
            UriComponentsBuilder uriBuilder) {
        Acomodacao acomodacaoLocal = repository.save(acomodacao);
        var uri = uriBuilder.path("/acomodacao/{id}").buildAndExpand(acomodacaoLocal.getId()).toUri();
        return ResponseEntity.created(uri).build();
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<Object> excluir(@PathVariable Long id) {
        var acomodacao = repository.getReferenceById(id);
        repository.delete(acomodacao);
        return ResponseEntity.noContent().build();
    }

    @PutMapping
    @Transactional
    public ResponseEntity<Acomodacao> atualizar(@RequestBody @Valid Acomodacao acomodacao) {
        Acomodacao acomodacaoLocal = repository.findById(
                acomodacao.getId()).get();

        acomodacaoLocal.setNome(acomodacao.getNome());

        return ResponseEntity.ok(acomodacaoLocal);
    }
}