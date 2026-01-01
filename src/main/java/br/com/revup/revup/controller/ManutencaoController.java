package br.com.revup.revup.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.querydsl.binding.QuerydslPredicate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.querydsl.core.types.Predicate;

import br.com.revup.revup.controller.request.CriarManutencaoRequest;
import br.com.revup.revup.controller.request.EditarManutencaoRequest;
import br.com.revup.revup.entity.Manutencao;
import br.com.revup.revup.service.ManutencaoService;
import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;


@RestController
@RequestMapping("/manutencao")
public class ManutencaoController {
    // Service
    @Autowired
    private ManutencaoService manutencaoService;

    // Rotas
    @PostMapping
    public ResponseEntity<String> criarManutencao (@RequestBody @Valid CriarManutencaoRequest dto) {
        manutencaoService.criarManutencao(dto.paraEntidade());
        
        return new ResponseEntity<String>(
            "Manutenção " + dto.servico() + " criada com sucesso.",
            HttpStatus.CREATED
        );
    }

    @GetMapping
    public ResponseEntity<Page<Manutencao>> listarManutencoes (Pageable pageable) {
        return new ResponseEntity<Page<Manutencao>>(
            manutencaoService.listarManutencoes(pageable),
            HttpStatus.OK
        );
    }
    
    @GetMapping("/filtro")
    public ResponseEntity<Page<Manutencao>> listarManutencoesFiltro (@QuerydslPredicate(root = Manutencao.class) Predicate predicate, Pageable pageable) {
        return new ResponseEntity<Page<Manutencao>>(
            manutencaoService.listarManutencoesFiltro(predicate, pageable),
            HttpStatus.OK
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> editarManutencao (@PathVariable long id, @RequestBody @Valid EditarManutencaoRequest dto) {
        manutencaoService.editarManutencao(id, dto.paraEntidade());
        
        return new ResponseEntity<String>(
            "Manutenção com id " + id + " editada com sucesso.",
            HttpStatus.OK
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletarManutencao (@PathVariable long id) {
        manutencaoService.deletarManutencao(id);
        
        return new ResponseEntity<String>(
            "Manutenção com id " + id + " deletada com sucesso.",
            HttpStatus.OK
        );
    }
}
