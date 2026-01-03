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

import br.com.revup.revup.controller.request.CriarLavagemRequest;
import br.com.revup.revup.controller.request.EditarLavagemRequest;
import br.com.revup.revup.entity.Lavagem;
import br.com.revup.revup.service.LavagemService;
import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;



@RestController
@RequestMapping("/lavagem")
public class LavagemController {
    // Service
    @Autowired
    private LavagemService lavagemService;

    @PostMapping
    public ResponseEntity<String> criarLavagem (@RequestBody @Valid CriarLavagemRequest dto) {
        lavagemService.criarLavagem(dto.paraEntidade());
        
        return new ResponseEntity<String>(
            "Lavagem criada com sucesso.",
            HttpStatus.CREATED
        );
    }

    @GetMapping
    public ResponseEntity<Page<Lavagem>> listarLavagens (Pageable pageable) {
        return new ResponseEntity<Page<Lavagem>>(
            lavagemService.listarLavagens(pageable),
            HttpStatus.OK
        );
    }
    
    @GetMapping("/filtro")
    public ResponseEntity<Page<Lavagem>> listarLavagensFiltro (@QuerydslPredicate(root = Lavagem.class) Predicate predicate, Pageable pageable) {
        return new ResponseEntity<Page<Lavagem>>(
            lavagemService.listarLavagensFiltro(predicate, pageable),
            HttpStatus.OK
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> editarLavagem (@PathVariable long id, @RequestBody @Valid EditarLavagemRequest dto) {
        lavagemService.editarLavagem(id, dto.paraEntidade());
        
        return new ResponseEntity<String>(
            "Lavagem com id "+ id +" editada com sucesso.",
            HttpStatus.OK
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletarLavagem (@PathVariable long id) {
        lavagemService.deletarLavagem(id);
        
        return new ResponseEntity<String>(
            "Lavagem com id "+ id +" deletada com sucesso.",
            HttpStatus.OK
        );
    }
}
